package com.melvin.ongandroid.model.NosotrosActivities

import com.google.gson.annotations.SerializedName

class Members(
    @SerializedName("data")
    val members: List<MembersList>
)
