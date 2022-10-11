package com.melvin.ongandroid.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentSignupBinding
import com.melvin.ongandroid.utils.Extensions.logEventInFirebase
import com.melvin.ongandroid.utils.ResultState
import com.melvin.ongandroid.viewmodel.singup.SingUpViewModel
import com.melvin.ongandroid.viewmodel.singup.SingUpViewModelFactory

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAnalytic: FirebaseAnalytics

    private val viewModel: SingUpViewModel by viewModels(
        factoryProducer = { SingUpViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAnalytic = Firebase.analytics

        setupClickListeners()

        setupOnTextChangeListener()
    }

    /*
     * Setup on click listeners of the fragment
     */
    private fun setupClickListeners() {
        binding.apply {
            tvingresa.setOnClickListener { navigateLoginFragment() }
            buttonRegister.setOnClickListener {
                viewModel.registerUser()
                checkRegisterResult()
                logEventInFirebase(firebaseAnalytic, "register_pressed")
            }
        }
    }

    /*
     * Setup on text change listener to update Live Data SingUpDto at View Model
     */
    private fun setupOnTextChangeListener() {
        binding.apply {
            tInputName.addTextChangedListener { text ->
                text?.apply { viewModel.updateName(text.toString()) }
            }
            tInputEmail.addTextChangedListener { text ->
                text?.apply { viewModel.updateEmail(text.toString()) }
            }
            tInputPassword.addTextChangedListener { text ->
                text?.apply { viewModel.updatePassword(text.toString()) }
            }
            tInputRepeatPassword.addTextChangedListener { text ->
                text?.apply { viewModel.updateRepeatPassword(text.toString()) }
            }
        }
    }

    /*
     * Observes the result of the user registration Live Data from View Model
     */
    private fun checkRegisterResult() {
        viewModel.registerResultState.observe(this.viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Loading -> {}
                is ResultState.Success -> {
                    if(!viewModel.succesDialogShown.value!!) {
                        showSuccesDialog()
                        logEventInFirebase(firebaseAnalytic, "sign_up_success")
                        viewModel.succesDialogShown.value = true
                    }
                }
                is ResultState.Error -> {
                    showErrorDialog(resultState.exception.message.toString())
                    logEventInFirebase(firebaseAnalytic, "sign_up_error")
                }
            }
        }
    }
    //ticket OT306-25
    private fun showErrorDialog(errorMessage: String) {

        val builder = AlertDialog.Builder(context)
                builder.setTitle("Error")
                        .setMessage(errorMessage)
                        .setPositiveButton("Volver al registro",null)
                        .show()
        binding.tInputEmail.error  = "ingrese un nuevo email"
        binding.tInputName.error = "error en el registro"
        binding.tInputRepeatPassword.error = "error en el registro"
        binding.tInputPassword.error = "error en el registro"

        binding.tInputEmail.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //nothing to do
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tInputName.error = null
                binding.tInputRepeatPassword.error = null
                binding.tInputPassword.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                //nothing to do
            }
        } )
    }

    /*
     * Navigates to the Login Fragment
     */
    private fun navigateLoginFragment() {
        val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    /*
     * Call this function after succes registration result.
     * This dialog inform the succes result to the user
     * and on close event navigates to the Login Fragment.
     */
    private fun showSuccesDialog() {
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage(getString(R.string.register_successfully))
            .setPositiveButton(getString(R.string.dialog_acept)) { _,_ ->
                navigateLoginFragment()
            }.create()
            .show()
    }

}