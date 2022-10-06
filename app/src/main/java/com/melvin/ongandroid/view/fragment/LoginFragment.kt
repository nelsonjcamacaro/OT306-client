package com.melvin.ongandroid.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.LoginValidationForm
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.melvin.ongandroid.businesslogic.login.LoginUseCase
import com.melvin.ongandroid.model.login.LoginRepository
import com.melvin.ongandroid.model.login.LoginViewState
import com.melvin.ongandroid.model.login.SharedPreferences
import com.melvin.ongandroid.model.news.RetrofitClient
import com.melvin.ongandroid.viewmodel.login.LoginViewModel
import com.melvin.ongandroid.viewmodel.login.LoginViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginValidationForm: LoginValidationForm
    private val loginViewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(LoginUseCase(LoginRepository(RetrofitClient.webservice)),
            SharedPreferences(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignUp()
        loginValidationForm = LoginValidationForm()
        enableButton()
        binding.buttonLogin.setOnClickListener {
            Toast.makeText(context, "boton habilitado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSignUp() {
        binding.textAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            findNavController().navigate(action)

            setupLogin(requireContext())
            setupLoginObserver()
        }
    }

    // login view state
    private fun setupLoginObserver() {
        loginViewModel.loginViewState.observe(viewLifecycleOwner, Observer { viewState ->
            when (viewState) {
                is LoginViewState.Loading -> {
                    // login progress bar
                }
                is LoginViewState.Content -> {
                    // si el login es correcto navegar a home.
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
                is LoginViewState.Error -> {
                    // error message
                }
            }
        })
    }

    // setup button login
    private fun setupLogin(context: Context) {
        binding.buttonLogin.setOnClickListener {
            val email = binding.inputTextEmail.text.toString().trim()
            val password = binding.inputTextPassword.text.toString().trim()
            loginViewModel.loginUser(email, password, context)
        }
    }


    fun enableButton() {
        val email = binding.inputTextEmail
        val password = binding.inputTextPassword
        val button = binding.buttonLogin
        var emailValidated: Boolean
        var passwordValidated: Boolean


        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
                //nothing to do
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailValidated = loginValidationForm.emailValidation(email)
                passwordValidated = loginValidationForm.passwordValidation(password)
                if (emailValidated && passwordValidated) button.isEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {
                passwordValidated = loginValidationForm.passwordValidation(password)
                emailValidated = loginValidationForm.emailValidation(email)
                if (!emailValidated || !passwordValidated) button.isEnabled = false
            }

        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
                //nothing to do
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordValidated = loginValidationForm.passwordValidation(password)
                emailValidated = loginValidationForm.emailValidation(email)
                if (emailValidated && passwordValidated) button.isEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {
                passwordValidated = loginValidationForm.passwordValidation(password)
                emailValidated = loginValidationForm.emailValidation(email)
                if (!emailValidated || !passwordValidated) button.isEnabled = false
            }
        })
    }
}
