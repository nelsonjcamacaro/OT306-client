package com.melvin.ongandroid.model.login

import android.content.Context

class SharedPreferences(val context: Context) {

    val user_token = "token"

    val saveToken = context.getSharedPreferences("user_token", Context.MODE_PRIVATE)

    fun userToken(token: String) {
        saveToken.edit().putString(this.user_token, token).apply()
    }

    fun getUserToken(): String? {
        return saveToken.getString(this.user_token, null)
    }
}