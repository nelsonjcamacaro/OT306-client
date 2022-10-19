package com.melvin.ongandroid.model.news

import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("category_id")
    val category_id: Int? = -1,
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("created_at")
    val created_at: String? = "",
    @SerializedName("deleted_at")
    val deleted_at: Any? = "",
    @SerializedName("group_id")
    val group_id: Any? = "",
    @SerializedName("id")
    val id: Int? = -1,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("update_at")
    val updated_at: String? = "",
    @SerializedName("user_id")
    val user_id: Any? = ""
)

data class NewsResponse(
    @SerializedName("data")
    val data: List<NewsModel> = listOf(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("success")
    val success: Boolean = false
)