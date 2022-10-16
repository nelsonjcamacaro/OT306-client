package com.melvin.ongandroid.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.melvin.ongandroid.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }



    @Deprecated("Deprecated in Java", ReplaceWith("finishAffinity()"))
    override fun onBackPressed() {
        finishAffinity()
    }

}