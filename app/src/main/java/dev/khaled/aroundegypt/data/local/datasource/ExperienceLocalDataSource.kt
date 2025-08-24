package dev.khaled.aroundegypt.data.local.datasource

import dev.khaled.aroundegypt.data.local.room.entity.RecommendedExperienceEntity
import dev.khaled.aroundegypt.data.local.room.entity.RecentExperienceEntity
import dev.khaled.aroundegypt.domain.model.Experience
import kotlinx.coroutines.flow.Flow

interface ExperienceLocalDataSource {
    
    fun getRecommendedExperiences(): Flow<List<RecommendedExperienceEntity>>
    
    fun getRecentExperiences(): Flow<List<RecentExperienceEntity>>
    
    suspend fun updateLikeStatus(experienceId: String, isLiked: Boolean, likesCount: Int)
    
    suspend fun cacheRecommendedExperiences(experiences: List<Experience>)
    
    suspend fun cacheRecentExperiences(experiences: List<Experience>)
}
