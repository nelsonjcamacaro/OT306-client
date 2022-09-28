package com.melvin.ongandroid.model.nosotrosActivities.model

import com.google.gson.annotations.SerializedName

data class MembersResponse(
    @SerializedName ("success")
    val succes: Boolean = false,
    @SerializedName ("data")
    val members: List<MemberDto> = emptyList(),
    @SerializedName ("message")
    val message : String? = ""
)
