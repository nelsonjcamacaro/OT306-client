package com.melvin.ongandroid.view.fragment

import android.content.Context
import android.content.Intent
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
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import com.melvin.ongandroid.businesslogic.login.LoginUseCase
import com.melvin.ongandroid.model.login.LoginRepository
import com.melvin.ongandroid.model.login.LoginViewState
import com.melvin.ongandroid.model.login.SharedPreferences
import com.melvin.ongandroid.model.news.RetrofitClient
import com.melvin.ongandroid.utils.Extensions
import com.melvin.ongandroid.view.MainActivity
import com.melvin.ongandroid.viewmodel.login.LoginViewModel
import com.melvin.ongandroid.viewmodel.login.LoginViewModelFactory
import java.util.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    // instancia callback facebook
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var loginValidationForm: LoginValidationForm
    private lateinit var auth: FirebaseAuth

    private lateinit var firebaseAnalytic: FirebaseAnalytics
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private val GOOGLE_SIGN_IN = 100
    private val loginViewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(
            LoginUseCase(LoginRepository(RetrofitClient.webservice)),
            SharedPreferences(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // login manager configuration
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {}
                override fun onError(error: FacebookException) {
                    Extensions.logEventInFirebase(firebaseAnalytic, "log_in_error")
                    Toast.makeText(context,"error de  autenticacion",Toast.LENGTH_LONG).show()
                }
                override fun onSuccess(result: LoginResult) {
                    Extensions.logEventInFirebase(firebaseAnalytic, "log_in_success")
                    startActivity(Intent(context, MainActivity::class.java))
                }
            })

        // facebook login button
        binding.buttonFacebook.setOnClickListener {
            Extensions.logEventInFirebase(firebaseAnalytic, "facebook_pressed")
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile"));
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytic = Firebase.analytics
        setupSignUp()
        loginValidationForm = LoginValidationForm()
        auth = FirebaseAuth.getInstance()
        enableButton()
        binding.buttonLogin.setOnClickListener {
            Extensions.logEventInFirebase(firebaseAnalytic, "log_in_pressed")
            /*Momentaneamente mientras se realiza la integracion de registro,
             con colocar email y contraseña en el login llevara directo al home*/
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        binding.buttonGoogle.setOnClickListener {
            Extensions.logEventInFirebase(firebaseAnalytic, "gmail_pressed")
            loginWithGoogle()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)

                if (account!= null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            Extensions.logEventInFirebase(firebaseAnalytic, "log_in_success")
                            val intent = Intent(context,MainActivity::class.java)
                            startActivity(intent)
                        }else {
                            Extensions.logEventInFirebase(firebaseAnalytic, "log_in_error")
                            Toast.makeText(context,"error de  autenticacion",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }catch (e:ApiException){
                Extensions.logEventInFirebase(firebaseAnalytic, "log_in_error")
                Toast.makeText(context,"error de  autenticacion",Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun loginWithGoogle() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = context?.let { GoogleSignIn.getClient(it, googleSignInOptions) }!!
        startActivityForResult(googleSignInClient.signInIntent, GOOGLE_SIGN_IN)
    }


    private fun setupSignUp() {
        binding.textAccount.setOnClickListener {
            Extensions.logEventInFirebase(firebaseAnalytic, "sing_up_pressed")
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
                is LoginViewState.Loading -> {}
                is LoginViewState.Content -> {
                    Extensions.logEventInFirebase(firebaseAnalytic, "log_in_succes")
                    successDialogLogin()
                }
                is LoginViewState.Error -> {
                    Extensions.logEventInFirebase(firebaseAnalytic, "log_in_error")
                    errorDialogLogin()
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
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

    private fun errorDialogLogin() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Error")
                .setMessage("Error endpoint")
                .setPositiveButton("LoginFragement close", null)
                .show()
            binding.inputTextEmail.error = "Error"
            binding.inputTextPassword.error = "Error"
        }
    }

    private fun successDialogLogin() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
}