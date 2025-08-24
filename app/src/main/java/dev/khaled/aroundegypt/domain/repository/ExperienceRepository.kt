package dev.khaled.aroundegypt.domain.repository

import dev.khaled.aroundegypt.domain.model.Experience
import kotlinx.coroutines.flow.Flow

interface ExperienceRepository {
    
    fun getRecommendedExperiences(): Flow<List<Experience>>
    
    fun getRecentExperiences(): Flow<List<Experience>>
    
    fun searchExperiences(query: String): Flow<List<Experience>>
    
    fun getExperienceById(id: String): Flow<Experience>
    
    fun likeExperience(id: String): Flow<Int>
}
