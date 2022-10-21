package com.melvin.ongandroid.businesslogic

import android.widget.EditText
import androidx.core.util.PatternsCompat

class LoginValidationForm {

    fun emailValidation(editText: EditText):Boolean{

        val email= editText.text.toString()
        return if (email.isEmpty()){
            editText.error = "el email no puede ser vacio"
            false
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            editText.error = "formato de email no valido"
            false
        }else  {
            editText.error = null
            true

        }
    }

    fun passwordValidation(editText: EditText):Boolean{
        val password= editText.text.toString()

        return if (password.isEmpty()){
            //editText.error = "la contraseña no puede ser vacia"
            editText.setError("la contraseña no puede ser vacia",null)
            false
        }else if (!password.matches(".*[0-9].*".toRegex())){
            //editText.error = "la contraseña debe tener al menos un numero"
            editText.setError("la contraseña debe tener al menos un numero",null)
            false
        }else if (!password.matches(".*[a-z].*".toRegex())){
            editText.setError("la contraseña debe tener al menos una letra",null)
            false
        }else{
            editText.error = null
            true
        }
    }
}