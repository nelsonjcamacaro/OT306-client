package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melvin.ongandroid.businesslogic.IsInputValidUseCase
import com.melvin.ongandroid.model.ContactMessageDto
import com.melvin.ongandroid.model.OngRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
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
class ContactViewModelTest {

    @RelaxedMockK
    private lateinit var repository: OngRepository

    @RelaxedMockK
    private lateinit var isInputValidUseCase: IsInputValidUseCase

    private lateinit var contactViewModel: ContactViewModel

    @get: Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        contactViewModel = ContactViewModel(repository, isInputValidUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenTheEmailIsInvalid() = runTest {
        // Given
        val data = ContactMessageDto(1, "hugo", "", "esto es un mensaje")

        // When
        contactViewModel.updateEmail(data.email)

        // Then
        assert(contactViewModel.isValidInput.value == false)
    }

    @Test
    fun whenTheNameIsEmpty() = runTest {
        // Given
        val data = ContactMessageDto(1, "hugo", "hugo@gmail.com", "esto es un mensaje")

        // When
        contactViewModel.updateName(data.nameAndLastName)

        // Then
        assert(contactViewModel.isValidInput.value == false)
    }

    @Test
    fun whenTheMessageIsEmpty() = runTest {
        // Given
        val data = ContactMessageDto(1, "hugo", "hugo@gmail.com", "")

        // When
        contactViewModel.updateMessage(data.message)

        // Then
        assert(contactViewModel.isValidInput.value == false)
    }

    @Test
    fun whenTheInformationIsCorrect() = runTest {
        // Given
        val data = ContactMessageDto(1, "hugo", "hugo@gmail.com", "esto es un mensaje")

        // When
        contactViewModel.updateName(data.nameAndLastName)
        contactViewModel.updateEmail(data.email)
        contactViewModel.updateMessage(data.message)

        // Then
        val result = ContactMessageDto(1, "hugo", "hugo@gmail.com", "esto es un mensaje")
        assertEquals(result, data)
    }
}












