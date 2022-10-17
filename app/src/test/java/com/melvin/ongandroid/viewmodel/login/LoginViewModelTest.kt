package com.melvin.ongandroid.viewmodel.login
import com.melvin.ongandroid.businesslogic.login.LoginUseCase
import com.melvin.ongandroid.model.login.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @RelaxedMockK
    private lateinit var loginUseCase : LoginUseCase

    @RelaxedMockK
    private lateinit var sharedPreferences: SharedPreferences


    lateinit var loginViewModel: LoginViewModel

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        loginViewModel = LoginViewModel(loginUseCase, sharedPreferences)
    }

    @Test
    fun `invalid Password`()= runTest{
        val result = loginViewModel.validateLogin(email = "123@gmail.com", password = "9547lo")
        assertEquals(false,result)

    }

    @Test
    fun `valid Password`()= runTest{
        val result = loginViewModel.validateLogin(email = "123@gmail.com", password = "aA11aasasas#Awf")
        assertEquals(true, result)
    }

    @Test
    fun `invalid Email`()= runTest{
        val result = loginViewModel.validateLogin(email = "12gmail.com", password = "9547lo")
        assertEquals(false,result)
    }

    @Test
    fun `valid Email`()= runTest{
        val result = loginViewModel.validateLogin(email = "123@gmail.com", password = "aA11aasasas#Awf")
        assertEquals(true,result)
    }

    @Test
    fun `password Empty`()= runTest{
        val result = loginViewModel.validateLogin(email = "123@gmail.com", password = "")
        assertEquals(false, result)
    }

    @Test
    fun `email Empty`()= runTest{
        val result = loginViewModel.validateLogin(email = "", password = "aA11aasasas#Awf")
        assertEquals(false, result)
    }
}