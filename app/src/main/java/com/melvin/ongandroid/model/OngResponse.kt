package com.melvin.ongandroid.model

import com.google.gson.annotations.SerializedName

data class OngResponse(
    @SerializedName("success")
    val success: Boolean? = false,
    @SerializedName("data")
    val data: Any? = "",
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("errors")
    val errors: Errors? = Errors()
)

data class Errors(
    val email: String = ""
)