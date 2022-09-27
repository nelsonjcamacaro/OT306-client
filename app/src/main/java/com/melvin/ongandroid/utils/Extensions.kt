package com.melvin.ongandroid.utils

import android.app.AlertDialog
import android.content.Context

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
}