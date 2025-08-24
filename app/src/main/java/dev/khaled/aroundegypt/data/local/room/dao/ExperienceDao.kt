package dev.khaled.aroundegypt.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.khaled.aroundegypt.data.local.room.entity.RecentExperienceEntity
import dev.khaled.aroundegypt.data.local.room.entity.RecommendedExperienceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExperienceDao {
    
    @Query("SELECT * FROM recommended_experiences")
    fun getRecommendedExperiences(): Flow<List<RecommendedExperienceEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendedExperiences(experiences: List<RecommendedExperienceEntity>)
    
    @Query("DELETE FROM recommended_experiences")
    suspend fun clearRecommendedExperiences()
    
    @Transaction
    suspend fun cacheRecommendedExperiences(experiences: List<RecommendedExperienceEntity>) {
        clearRecommendedExperiences()
        insertRecommendedExperiences(experiences)
    }
    
    @Query("SELECT * FROM recent_experiences")
    fun getRecentExperiences(): Flow<List<RecentExperienceEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentExperiences(experiences: List<RecentExperienceEntity>)
    
    @Query("DELETE FROM recent_experiences")
    suspend fun clearRecentExperiences()
    
    @Transaction
    suspend fun cacheRecentExperiences(experiences: List<RecentExperienceEntity>) {
        clearRecentExperiences()
        insertRecentExperiences(experiences)
    }
    
    @Query("UPDATE recommended_experiences SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :experienceId")
    suspend fun updateRecommendedExperienceLikeStatus(experienceId: String, isLiked: Boolean, likesCount: Int)
    
    @Query("UPDATE recent_experiences SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :experienceId")
    suspend fun updateRecentExperienceLikeStatus(experienceId: String, isLiked: Boolean, likesCount: Int)
    
    suspend fun updateLikeStatus(experienceId: String, isLiked: Boolean, likesCount: Int) {
        updateRecommendedExperienceLikeStatus(experienceId, isLiked, likesCount)
        updateRecentExperienceLikeStatus(experienceId, isLiked, likesCount)
    }
}
