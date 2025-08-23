package dev.khaled.aroundegypt.data.repository

import dev.khaled.aroundegypt.data.model.Experience
import dev.khaled.aroundegypt.data.service.ExperienceApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExperienceRepositoryImpl @Inject constructor(
    private val apiService: ExperienceApiService
) : ExperienceRepository {

    override fun getRecommendedExperiences(): Flow<List<Experience>> = flow {
        val response = apiService.getRecommendedExperiences()
        if (response.isSuccessful) {
            val experiences = response.body()?.data ?: emptyList()
            emit(experiences)
        } else {
            throw Exception("Failed to [fetch recommended experiences] : error code ${response.code()}")
        }
    }.catch { exception ->
        throw exception
    }
    
    override fun getRecentExperiences(): Flow<List<Experience>> = flow {
        val response = apiService.getRecentExperiences()
        if (response.isSuccessful) {
            val experiences = response.body()?.data ?: emptyList()
            emit(experiences)
        } else {
            throw Exception("Failed to [fetch recent experiences] : error code ${response.code()}")
        }
    }.catch { exception ->
        throw exception
    }
    
    override fun searchExperiences(query: String): Flow<List<Experience>> = flow {
        val response = apiService.searchExperiences(query)
        if (response.isSuccessful) {
            val experiences = response.body()?.data ?: emptyList()
            emit(experiences)
        } else {
            throw Exception("Failed to [search experiences] : error code ${response.code()}")
        }
    }.catch { exception ->
        throw exception
    }
    
    override fun getExperienceById(id: String): Flow<Experience> = flow {
        val response = apiService.getExperienceById(id)
        if (response.isSuccessful) {
            val experience = response.body()?.data
            if (experience != null) {
                emit(experience)
            } else {
                throw Exception("Experience not found")
            }
        } else {
            throw Exception("Failed to [fetch experience by id] : error code ${response.code()}")
        }
    }.catch { exception ->
        throw exception
    }
    
    override fun likeExperience(id: String): Flow<Int> = flow {
        val response = apiService.likeExperience(id)
        if (response.isSuccessful) {
            val likesCount = response.body()?.likesCount
            if (likesCount != null) {
                emit(likesCount)
            } else {
                throw Exception("Invalid like response")
            }
        } else {
            throw Exception("Failed to [like experience] : error code ${response.code()}")
        }
    }.catch { exception ->
        throw exception
    }
}
