package com.melvin.ongandroid.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.melvin.ongandroid.businesslogic.login.LoginUseCase
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.model.login.LoginRepository
import com.melvin.ongandroid.model.login.LoginViewState
import com.melvin.ongandroid.model.login.SharedPreferences
import com.melvin.ongandroid.model.news.RetrofitClient
import com.melvin.ongandroid.viewmodel.login.LoginViewModel
import com.melvin.ongandroid.viewmodel.login.LoginViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(LoginUseCase(LoginRepository(RetrofitClient.webservice)),
            SharedPreferences(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        setupSignUp()
        setupLogin(requireContext())
        setupLoginObserver()
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

    private fun setupSignUp() {
        binding.textAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            findNavController().navigate(action)
        }
    }
}