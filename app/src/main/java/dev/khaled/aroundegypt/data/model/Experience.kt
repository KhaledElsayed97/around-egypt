package dev.khaled.aroundegypt.data.model

import com.google.gson.annotations.SerializedName

data class Experience(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover_photo")
    val imageUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("likes_no")
    val likesCount: Int,
    @SerializedName("is_liked")
    val isLiked: Boolean? = false,
)
