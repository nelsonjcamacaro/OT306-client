package com.melvin.ongandroid.model.nosotrosActivities.model

import com.google.gson.annotations.SerializedName
import com.melvin.ongandroid.utils.AppConstants

data class MemberDto(
    @SerializedName ("id")
    val id: Int = -1,
    @SerializedName ("name")
    val name: String = "",
    @SerializedName ("image")
    val image: String = "",
    @SerializedName ("description")
    val description: String = "",
    @SerializedName ("facebookUrl")
    val facebookUrl: String = "",
    @SerializedName ("linkedinUrl")
    val linkedinUrl: String = "",
    @SerializedName ("created_at")
    val created_at: String = "",
    @SerializedName ("updated_at")
    val updated_at: String? = "",
    @SerializedName ("deleted_at")
    val deleted_at: String? = "",
    @SerializedName ("group_id")
    val group_id: Any? = ""
)

fun MemberDto.getFormattedDescription() = this.description.replace(AppConstants.FORMAT_DESCRIPTION, "")