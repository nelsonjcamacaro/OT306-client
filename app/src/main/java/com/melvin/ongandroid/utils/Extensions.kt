package com.melvin.ongandroid.utils

import android.os.Bundle
import android.text.Html
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.R

object Extensions {

    /*
     * Reusable snack bar for all fragments.
     * Call without message value to show default error message or set your custom message
     */
    fun errorSnackBar(view: View, customMessage: String = "", reloadData: () -> Unit) {
        var message = view.context.getString(R.string.error_message_getting_data)
        if (customMessage != "") message = customMessage
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(AppConstants.POSITIVE_BUTTON) {
                reloadData()
            }
            .show()
    }

    /*
     *  Extension function to log event in Firebase Analytics.
     *  Call this function from a Fragment Class with String of event to log.
     */
    fun logEventInFirebase(firebaseAnalytic: FirebaseAnalytics, event: String){
        val bundle = Bundle()
        bundle.putString("eventLog", event)
        firebaseAnalytic.logEvent(event, bundle)
    }

    /*
     * Extension function to format any content from Server
     * Call this function from a String to get a formatted text content
     */
    fun String?.formatDescription() =
        Html.fromHtml(this ?: "").replace(Regex("\\n"), " ")

}