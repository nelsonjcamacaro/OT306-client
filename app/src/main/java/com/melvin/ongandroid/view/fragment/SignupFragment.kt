package com.melvin.ongandroid.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentSignupBinding
import com.melvin.ongandroid.utils.AppConstants
import com.melvin.ongandroid.utils.ResultState
import com.melvin.ongandroid.viewmodel.singup.SingUpViewModel
import com.melvin.ongandroid.viewmodel.singup.SingUpViewModelFactory

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

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
                is ResultState.Success -> showSuccesDialog()
                is ResultState.Error -> showErrorDialog()
            }
        }
    }

    private fun showErrorDialog() {

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
            }.setCancelable(true)
        builder.create().show()
    }

}