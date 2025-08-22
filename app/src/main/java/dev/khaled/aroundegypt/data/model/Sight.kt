package dev.khaled.aroundegypt.data.model

data class Sight(
    val id: String,
    val name: String,
    val imageUrl: String,
    val likesCount: Int,
    val description: String,
    val isLiked: Boolean = false
)
