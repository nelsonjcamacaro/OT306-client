package com.melvin.ongandroid.utils

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE
import com.melvin.ongandroid.R

/*
*  Use start with your imageView container for loading animation
*  Use stop to disable animation. If don´t set a restore resource Id, the imageview will be Gone
*
*  Example
*  =======
*  val spinner = LoadingSpinner()
*  spinner.start(binding.imageLogo)
*  binding.imageLogo.setOnClickListener {
*      spinner.stop(it as ImageView, R.drawable.logo)
*  }
*/

class LoadingSpinner {

    fun start(imageView: ImageView) {
        imageView.visibility = View.VISIBLE
        imageView.setImageResource(R.drawable.avd_logo_spinner)
        val animatedLogo = imageView.drawable as? Animatable
        animatedLogo?.start()
    }

    fun stop(imageView: ImageView, restoreResourceId: Int? = null) {
        val animatedLogo = imageView.drawable as? Animatable
        animatedLogo?.stop()
        if (restoreResourceId == null) {
            imageView.visibility = View.GONE
        } else {
            imageView.setImageResource(restoreResourceId)
        }

    }

}