package dev.khaled.aroundegypt.data.model

import com.google.gson.annotations.SerializedName

data class ExperienceResponse(
    @SerializedName("data")
    val data: List<Experience>? = null
)

data class SingleExperienceResponse(
    @SerializedName("data")
    val data: Experience? = null
)

data class LikeResponse(
    @SerializedName("data")
    val likesCount: Int? = null
)
