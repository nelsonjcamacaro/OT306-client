package com.melvin.ongandroid.utils

import androidx.core.util.PatternsCompat
import com.melvin.ongandroid.model.Contact

object MessageValidation {

    fun isValidInputData(contact: Contact?) : Boolean {
        if (contact == null) return false
        return if (contact.name.isNullOrEmpty() || contact.email.isNullOrEmpty() || contact.message.isNullOrEmpty()) {
            false
        } else {
            PatternsCompat.EMAIL_ADDRESS.matcher(contact.email).matches()
        }
    }

}