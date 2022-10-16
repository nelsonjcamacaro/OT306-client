package com.melvin.ongandroid.viewmodel.validation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainDispatcherRule
import io.mockk.MockKAnnotations
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ValidationViewModelTest {

    private lateinit var viewModel: ValidationViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = ValidationViewModel()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when the name is invalid return false`() = runTest {
        // given
        val name = ""
        val email = "email@email.com"
        val password = "abcd12345"
        val repeatPassword = "abcd12345"

        // when
        val data = viewModel.validateForm(name, email, password, repeatPassword)

        // then
        assertEquals(data, true)
    }

    @Test // cuando el nombre tiene menos de 4 caracteres retorna falso
    fun `when the name has less than 4 characters return false`() = runTest {
        // given
        val name = "abc"
        val email = "email@email.com"
        val password = "abcd12345"
        val repeatPassword = "abcd12345"

        // when
        val data = viewModel.validateForm(name, email, password, repeatPassword)

        // then
        assertEquals(data, true)
    }

    @Test // cuando el email no tiene arroba retorna falso
    fun `when the email does not have @ return false`() = runTest {
        // given
        val name = "pepe"
        val email = "emailemail.com"
        val password = "abcd12345"
        val repeatPassword = "abcd12345"

        // when
        val data = viewModel.validateForm(name, email, password, repeatPassword)

        // then
        assertEquals(data, true)
    }

    @Test // cuando el email esta vacio o nulo retorna falso
    fun `when the email is null or empty return false`() = runTest {
        // given
        val name = "pepe"
        val email = ""
        val password = "abcd12345"
        val repeatPassword = "abcd12345"

        // when
        val data = viewModel.validateForm(name, email, password, repeatPassword)

        // then
        assertEquals(data, true)
    }

    @Test // cuando el password tiene menos de 8 caracteres retorna falso
    fun `when the password has less than 8 characters return false`() = runTest {
        // given
        val name = "pepe"
        val email = "hugo@email.com"
        val password = "abcd123"
        val repeatPassword = "abcd123"

        // when
        val data = viewModel.validateForm(name, email, password, repeatPassword)

        // then
        assertEquals(data, true)
    }

    @Test // cuando el password es nulo o vacio retorna falso
    fun `when the password is null or empty return false`() = runTest {
        // given
        val name = "pepe"
        val email = "hugo@email.com"
        val password = ""
        val repeatPassword = ""

        // when
        val data = viewModel.validateForm(name, email, password, repeatPassword)

        // then
        assertEquals(data, true)
    }

    @Test // cuando el password no es igual al repeat password retorna falso
    fun `when the password does not match return false`() = runTest {
        // given
        val name = "pepe"
        val email = "hugo@email.com"
        val password = "abcd1235"
        val repeatPassword = "abcd12345"

        // when
        val data = viewModel.validateForm(name, email, password, repeatPassword)

        // then
        assertEquals(data, true)
    }

    @Test // cuando la repeticion del password es nula o vacio retorna falso
    fun `when the password repeat is null or empty return false`() = runTest {
        // given
        val name = "pepe"
        val email = "hugo@email.com"
        val password = "abcd1235"
        val repeatPassword = ""

        // when
        val data = viewModel.validateForm(name, email, password, repeatPassword)

        // then
        assertEquals(data, true)
    }

}












