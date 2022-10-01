package com.melvin.ongandroid.utils

import android.graphics.drawable.Animatable
import android.view.View
import android.widget.ImageView
import com.melvin.ongandroid.R

/*
*  Use start with your imageView container for loading animation
*  Use stop to disable animation. If don´t set a restore resource Id, the imageview will be Gone
*
*  Example
*  =======
*  val spinner = LoadingSpinner()
*  spinner.start(binding.imageLogo)
*  binding.imageLogo.setOnClickListener { imageLogo ->
*      spinner.stop(imageLogo)
*  }
*/

class LoadingSpinner {

    fun start(imageView: ImageView) {
        imageView.visibility = View.VISIBLE
        imageView.setImageResource(R.drawable.avd_logo_spinner)
        val animatedLogo = imageView.drawable as? Animatable
        animatedLogo?.start()
    }

    fun stop(imageView: ImageView, restoreLogo: Boolean = true) {
        val animatedLogo = imageView.drawable as? Animatable
        animatedLogo?.stop()
        if (restoreLogo) {
            imageView.setImageResource(R.drawable.logo)
        } else {
            imageView.visibility = View.GONE
        }
    }

}