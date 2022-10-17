package com.melvin.ongandroid.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.login.SharedPreferences
import com.melvin.ongandroid.view.MainActivity
import com.melvin.ongandroid.view.login.LoginActivity
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    // firebase auth instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setTheme(R.style.splashTheme)
        timerSplash()
    }

    // TICKET OT306-30
    // show message timer
    private fun timerSplash() {
        val sharedPreferences = SharedPreferences(this@SplashActivity)
        lifecycleScope.launch {
            object : CountDownTimer(5000,1000) {
                override fun onTick(time: Long) {}
                override fun onFinish() {

                    auth = FirebaseAuth.getInstance()
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        updateUI(currentUser)
                    } else if (sharedPreferences.getUserToken().isNullOrEmpty()) {
                        goToLoginActivity()
                    } else {
                        goToMainActivity()
                    }
                    // show toast
                    Toast.makeText(this@SplashActivity, "Timer has finished", Toast.LENGTH_SHORT).show()
                    cancel()
                }
            }.start()

        }
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    /*
     * Call this function when there is no user token saved on the phone
     * because the user is not logged in yet.
     * This function will navigate to Login Activity.
     */
    private fun goToLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*
     * Call this function when there is a user token saved on the phone
     * because the user is already logged in.
     * This function will navigate to Main Activity.
     */
    private fun goToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}