package com.melvin.ongandroid.model

import com.google.gson.annotations.SerializedName

class TestimonialsResponse(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("data")
    val testimonialsList:List<Testimonial>,
    @SerializedName("message")
    val message:String
    )
