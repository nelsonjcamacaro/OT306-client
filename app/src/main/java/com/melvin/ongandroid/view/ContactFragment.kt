package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentActivitiesBinding
import com.melvin.ongandroid.databinding.FragmentContactBinding
import com.melvin.ongandroid.model.ContactMessageDto
import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.model.OngRepository
import com.melvin.ongandroid.viewmodel.ContactViewModel
import com.melvin.ongandroid.viewmodel.ContactViewModelFactory


class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding?= null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ContactViewModel> {
        ContactViewModelFactory(OngRepository(OngRemoteDataSource()))
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          binding.sendMessageBtn.setOnClickListener {
              sendMessageFromContact()
          }

        viewModel.messageFromContact.observe(viewLifecycleOwner, Observer {response ->
            if (response != null){
                executeAlertDialog()
                Toast.makeText(context, "envio exitoso", Toast.LENGTH_SHORT).show()

                Log.d("Main", response.message.toString())
                Log.d("Main", response.email.toString())
                Log.d("Main", response.nameAndLastName.toString())
            }else{
                Toast.makeText(context, "error en el envio", Toast.LENGTH_SHORT).show()
            }
// le da visibilidad al progress bar
            viewModel.isLoading.observe(viewLifecycleOwner, Observer {
                binding.contactProgressBar.isVisible = it
            })


        })



    }

// captura los textos del formulario para realizar el POST a la API
    private fun sendMessageFromContact(){
        val name = binding.nameET.text.toString()
        val email= binding.emailET.text.toString()
        val message = binding.messageET.text.toString()
        viewModel.pushPost(ContactMessageDto(nameAndLastName = name, email = email, message = message))
    }

    private fun executeAlertDialog(){
        //asignando valores al modal dialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Envio exitoso")
        builder.setMessage("¡Tu solicitud ha sido enviada con exito!\nPronto nos contactaremos con vos")
        builder.setPositiveButton("Aceptar",null)
        builder.show()
        binding.nameET.setText("")
        binding.emailET.setText("")
        binding.messageET.setText("")
    }
}