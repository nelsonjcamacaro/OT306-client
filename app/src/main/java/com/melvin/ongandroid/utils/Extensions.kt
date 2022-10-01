package com.melvin.ongandroid.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R

object Extensions {
    /** ticket OT306-42 + OT306-36 **/
    /** this function show alert message error api call **/
    private fun loadAlertData(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(AppConstants.SET_TITLE)
            .setMessage(AppConstants.SET_MESSAGE)
            .setPositiveButton(AppConstants.POSITIVE_BUTTON) { _, _ ->
                /** reload api call **/

            }
            .setNegativeButton(AppConstants.NEGATIVE_BUTTON) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

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

}