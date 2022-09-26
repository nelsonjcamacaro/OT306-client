package com.melvin.ongandroid.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


// Dto = Data Transfer Object , contiene el modelo de datos que debe hacerse a la llamada POST de la seccion contacto
data class ContactMessageDto(
    @SerializedName("id") val id:Int? = 0,
    @SerializedName("name") val nameAndLastName:String?= "",
    @SerializedName("email") val email:String? = "",
    @SerializedName("phone") val phone:String? = "",
    @SerializedName("message") val message:String? = "",
    @SerializedName("deleted_at") val deleted_at:String? = "",
    @SerializedName("created_at") val created_at:String? = "",
    @SerializedName("updated_at") val updated_at:String? = ""
)