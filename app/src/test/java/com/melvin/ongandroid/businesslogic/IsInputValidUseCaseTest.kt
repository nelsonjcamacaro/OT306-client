package com.melvin.ongandroid.businesslogic

import com.google.common.truth.Truth.assertThat
import com.melvin.ongandroid.model.ContactMessageDto

import org.junit.Test

class IsInputValidUseCaseTest {

    private val isInputValidUseCase = IsInputValidUseCase()

    @Test
    fun `wrong email input should return false`() {
        val result = isInputValidUseCase(
            ContactMessageDto(nameAndLastName= "hola", email = "mail @email.com", message = "No se escribir mi mail")
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `null email input should return false`() {
        val result =
            isInputValidUseCase(
                ContactMessageDto(nameAndLastName = "hola",  message = "No escribi mi mail")
            )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty message input should return false`() {
        val result =
            isInputValidUseCase(
                ContactMessageDto(nameAndLastName ="Nombre", email = "mail@email.com")
            )
        assertThat(result).isFalse()
    }

    @Test
    fun `null name input should return false`() {
        val result =
            isInputValidUseCase(
                ContactMessageDto(email = "mail@email.com", message = "Se escribir mi mail pero no tengo nombre")
            )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid input should return true`() {
        val result =
            isInputValidUseCase(
                ContactMessageDto(nameAndLastName = "Nombre", email = "mail@email.com", message = "Se escribir mi mail")
            )
        assertThat(result).isTrue()
    }

}