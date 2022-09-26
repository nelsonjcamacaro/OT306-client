package com.melvin.ongandroid.businesslogic

import androidx.core.util.PatternsCompat
import com.melvin.ongandroid.model.Contact
import com.melvin.ongandroid.model.ContactMessageDto

class IsInputValidUseCase {

    operator fun invoke(contact: ContactMessageDto?) : Boolean {
        if (contact == null) return false
        return if (contact.nameAndLastName.isNullOrEmpty() || contact.email.isNullOrEmpty() || contact.message.isNullOrEmpty()) {
            false
        } else {
            PatternsCompat.EMAIL_ADDRESS.matcher(contact.email).matches()
        }
    }

}