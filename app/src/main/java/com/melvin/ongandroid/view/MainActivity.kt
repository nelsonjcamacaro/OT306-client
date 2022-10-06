package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ActivityMainBinding
import com.melvin.ongandroid.utils.AppConstants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        // setup navigation toolbar hide menu section detail
        navController.addOnDestinationChangedListener { controller, destination, arguments->
            when(destination.id) {
                R.id.detailFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                } else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

    }

    // this function control back stack in fragment home and exit the app
    override fun onBackPressed() {
        when(navController.currentDestination?.id) {
            R.id.homeFragment -> {
                exitApp()
            } else -> {
            super.onBackPressed()
            }
        }
    }

    // show exit message
    private fun exitApp() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(AppConstants.EXIT_MESSAGE)
            .setTitle("Salir")
            .setCancelable(true)
            .setPositiveButton("Si") {_,_ ->
                finishAffinity()
            }
            .setNegativeButton("No") {dialog,_->
                dialog.cancel()
            }
        builder.create().show()
    }
}








