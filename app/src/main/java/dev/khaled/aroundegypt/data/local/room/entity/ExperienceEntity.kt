package dev.khaled.aroundegypt.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.khaled.aroundegypt.domain.model.Experience

@Entity(tableName = "recommended_experiences")
data class RecommendedExperienceEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val likesCount: Int,
    val isLiked: Boolean
) {
    fun toExperience(): Experience {
        return Experience(
            id = id,
            title = title,
            imageUrl = imageUrl,
            description = description,
            likesCount = likesCount,
            isLiked = isLiked
        )
    }
    
    companion object {
        fun fromExperience(experience: Experience): RecommendedExperienceEntity {
            return RecommendedExperienceEntity(
                id = experience.id,
                title = experience.title,
                imageUrl = experience.imageUrl,
                description = experience.description,
                likesCount = experience.likesCount,
                isLiked = experience.isLiked ?: false
            )
        }
    }
}

@Entity(tableName = "recent_experiences")
data class RecentExperienceEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val likesCount: Int,
    val isLiked: Boolean
) {
    fun toExperience(): Experience {
        return Experience(
            id = id,
            title = title,
            imageUrl = imageUrl,
            description = description,
            likesCount = likesCount,
            isLiked = isLiked
        )
    }
    
    companion object {
        fun fromExperience(experience: Experience): RecentExperienceEntity {
            return RecentExperienceEntity(
                id = experience.id,
                title = experience.title,
                imageUrl = experience.imageUrl,
                description = experience.description,
                likesCount = experience.likesCount,
                isLiked = experience.isLiked ?: false
            )
        }
    }
}
