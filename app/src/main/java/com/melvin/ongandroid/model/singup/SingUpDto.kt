package com.melvin.ongandroid.model.singup

import com.google.gson.annotations.SerializedName

data class SingUpDto(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("password")
    val password: String? = ""
)
