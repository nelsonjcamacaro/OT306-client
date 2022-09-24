package com.melvin.ongandroid.utils


import com.google.common.truth.Truth.assertThat
import com.melvin.ongandroid.model.Contact
import org.junit.Test

class MessageValidationTest {

    private val messageValidation = MessageValidation

    @Test
    fun `wrong email input should return false`() {
        val result = messageValidation.isValidInputData(
            Contact("hola", "mail @email.com", "No se escribir mi mail")
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `null email input should return false`() {
        val result =
            messageValidation.isValidInputData(
                Contact("hola", null, "No escribi mi mail")
            )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty message input should return false`() {
        val result =
            messageValidation.isValidInputData(
                Contact("Nombre", "mail@email.com", "")
            )
        assertThat(result).isFalse()
    }

    @Test
    fun `null name input should return false`() {
        val result =
            messageValidation.isValidInputData(
                Contact(null, "mail@email.com", "Se escribir mi mail pero no tengo nombre")
            )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid input should return true`() {
        val result =
            messageValidation.isValidInputData(
                Contact("Nombre", "mail@email.com", "Se escribir mi mail")
            )
        assertThat(result).isTrue()
    }

}