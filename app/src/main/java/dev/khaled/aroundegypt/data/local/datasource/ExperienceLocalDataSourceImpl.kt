package dev.khaled.aroundegypt.data.local.datasource

import dev.khaled.aroundegypt.data.local.room.dao.ExperienceDao
import dev.khaled.aroundegypt.data.local.room.entity.RecommendedExperienceEntity
import dev.khaled.aroundegypt.data.local.room.entity.RecentExperienceEntity
import dev.khaled.aroundegypt.domain.model.Experience
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExperienceLocalDataSourceImpl @Inject constructor(
    private val experienceDao: ExperienceDao
) : ExperienceLocalDataSource {
    
    override fun getRecommendedExperiences(): Flow<List<RecommendedExperienceEntity>> {
        return experienceDao.getRecommendedExperiences()
    }
    
    override fun getRecentExperiences(): Flow<List<RecentExperienceEntity>> {
        return experienceDao.getRecentExperiences()
    }
    
    override suspend fun updateLikeStatus(experienceId: String, isLiked: Boolean, likesCount: Int) {
        experienceDao.updateLikeStatus(experienceId, isLiked, likesCount)
    }
    
    override suspend fun cacheRecommendedExperiences(experiences: List<Experience>) {
        if (experiences.isNotEmpty()) {
            val entities = experiences.map { RecommendedExperienceEntity.fromExperience(it) }
            experienceDao.cacheRecommendedExperiences(entities)
        }
    }
    
    override suspend fun cacheRecentExperiences(experiences: List<Experience>) {
        if (experiences.isNotEmpty()) {
            val entities = experiences.map { RecentExperienceEntity.fromExperience(it) }
            experienceDao.cacheRecentExperiences(entities)
        }
    }
}
