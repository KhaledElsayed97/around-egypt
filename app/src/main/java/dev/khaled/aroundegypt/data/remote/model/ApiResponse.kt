package dev.khaled.aroundegypt.data.remote.model

import com.google.gson.annotations.SerializedName
import dev.khaled.aroundegypt.domain.model.Experience

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
