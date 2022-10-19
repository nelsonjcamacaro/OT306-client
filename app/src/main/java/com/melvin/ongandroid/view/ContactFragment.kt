package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.melvin.ongandroid.businesslogic.IsInputValidUseCase
import com.melvin.ongandroid.databinding.FragmentContactBinding
import com.melvin.ongandroid.model.ContactMessageDto
import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.model.OngRepository
import com.melvin.ongandroid.utils.LoadingSpinner
import com.melvin.ongandroid.viewmodel.ContactViewModel
import com.melvin.ongandroid.viewmodel.ContactViewModelFactory

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var loadingSpinner: LoadingSpinner
    private val viewModel by viewModels<ContactViewModel> {
        ContactViewModelFactory(OngRepository(OngRemoteDataSource()), IsInputValidUseCase())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingSpinner = LoadingSpinner()
        restoreUIWithUserInput()
        subscribeSendMessageBtn()

        binding.sendMessageBtn.setOnClickListener {
            sendMessageFromContact()
        }

        // le da visibilidad al progress bar
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.contactProgressBar.isVisible = it
        })

        viewModel.messageFromContact.observe(viewLifecycleOwner, Observer { response ->
            if (response != null) {
                setLoadingSpinner(true)
                executeAlertDialog()
                setLoadingSpinner(false)
            } else {
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        })
        setupEditText()
    }

    /*
     * Subscribe send message button to is Valid Input in ViewModel
     * for instant enabled and disabled button
     */
    private fun subscribeSendMessageBtn() {
        viewModel.isValidInput.observe(viewLifecycleOwner) {
            binding.sendMessageBtn.isEnabled = viewModel.isValidInput.value ?: false
        }
    }

    // captura los textos del formulario para realizar el POST a la API
    private fun sendMessageFromContact() {
        val name = binding.nameET.text.toString()
        val email = binding.emailET.text.toString()
        val message = binding.messageET.text.toString()
        viewModel.pushPost(
            ContactMessageDto(
                nameAndLastName = name,
                email = email,
                message = message
            )
        )
    }

    private fun executeAlertDialog() {
        //asignando valores al modal dialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Envio exitoso")
        builder.setMessage("¡Tu solicitud ha sido enviada con exito!\nPronto nos contactaremos con vos")
        builder.setPositiveButton("Aceptar", null)
        builder.show()
        binding.nameET.setText("")
        binding.emailET.setText("")
        binding.messageET.setText("")
    }

    /*
     * Setup on text change listener of de edit text
     */
    private fun setupEditText() {
        binding.apply {
            nameET.addTextChangedListener { text ->
                text?.apply { viewModel.updateName(text.toString()) }
                tvErrorMessage.visibility = View.GONE
            }
            emailET.addTextChangedListener { text ->
                text?.apply { viewModel.updateEmail(text.toString()) }
                tvErrorMessage.visibility = View.GONE
            }
            messageET.addTextChangedListener { text ->
                text?.apply { viewModel.updateMessage(text.toString()) }
                tvErrorMessage.visibility = View.GONE
            }
        }
    }

    /*
     * Restore user input if the view was destroyed
     */
    private fun restoreUIWithUserInput() {
        if (viewModel.contact.value != null) {
            binding.apply {
                if (viewModel.contact.value!!.nameAndLastName != null) {
                    nameET.setText(viewModel.contact.value!!.nameAndLastName!!.toString())
                }
                if (viewModel.contact.value!!.email != null) {
                    emailET.setText(viewModel.contact.value!!.email!!.toString())
                }
                if (viewModel.contact.value!!.message != null) {
                    messageET.setText(viewModel.contact.value!!.message!!.toString())
                }
            }
        }
    }

    /*
     * Restore user input at resume view
     */
    override fun onResume() {
        restoreUIWithUserInput()
        super.onResume()
    }

    private fun setLoadingSpinner(isLoading: Boolean) {
        if (isLoading) {
            loadingSpinner.start(binding.imageLogo)
        } else {
            loadingSpinner.stop(binding.imageLogo)
        }
    }
}
