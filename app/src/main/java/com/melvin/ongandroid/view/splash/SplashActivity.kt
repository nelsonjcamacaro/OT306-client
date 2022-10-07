package com.melvin.ongandroid.view.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.melvin.ongandroid.R
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        timerSplash()
    }

    // TICKET OT306-30
    // show message timer
    private fun timerSplash() {
        lifecycleScope.launch {
            object : CountDownTimer(5000,1000) {
                override fun onTick(time: Long) {}
                override fun onFinish() {
                    // show toast
                    Toast.makeText(this@SplashActivity, "Timer has finished", Toast.LENGTH_SHORT).show()
                    cancel()
                }
            }.start()
        }
    }
}