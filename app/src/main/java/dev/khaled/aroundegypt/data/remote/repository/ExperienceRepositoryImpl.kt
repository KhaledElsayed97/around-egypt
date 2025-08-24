package dev.khaled.aroundegypt.data.remote.repository

import dev.khaled.aroundegypt.data.local.datasource.ExperienceLocalDataSource
import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.data.remote.service.ExperienceApiService
import dev.khaled.aroundegypt.domain.repository.ExperienceRepository
import dev.khaled.aroundegypt.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExperienceRepositoryImpl @Inject constructor(
    private val apiService: ExperienceApiService,
    private val localDataSource: ExperienceLocalDataSource,
    private val networkUtils: NetworkUtils
) : ExperienceRepository {

    override fun getRecommendedExperiences(): Flow<List<Experience>> = flow {
        val cachedExperiences = localDataSource.getRecommendedExperiences().first()
        if (cachedExperiences.isNotEmpty()) {
            emit(cachedExperiences.map { it.toExperience() })
        }
        
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = apiService.getRecommendedExperiences()
                if (response.isSuccessful) {
                    val experiences = response.body()?.data ?: emptyList()
                    if (experiences.isNotEmpty()) {
                        localDataSource.cacheRecommendedExperiences(experiences)
                        emit(experiences)
                    }
                }
            } catch (exception: Exception) {
            }
        }
        
        if (cachedExperiences.isEmpty() && !networkUtils.isNetworkAvailable()) {
            emit(emptyList())
        }
    }
    
    override fun getRecentExperiences(): Flow<List<Experience>> = flow {
        val cachedExperiences = localDataSource.getRecentExperiences().first()
        if (cachedExperiences.isNotEmpty()) {
            emit(cachedExperiences.map { it.toExperience() })
        }
        
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = apiService.getRecentExperiences()
                if (response.isSuccessful) {
                    val experiences = response.body()?.data ?: emptyList()
                    if (experiences.isNotEmpty()) {
                        localDataSource.cacheRecentExperiences(experiences)
                        emit(experiences)
                    }
                }
            } catch (exception: Exception) {
            }
        }
        
        if (cachedExperiences.isEmpty() && !networkUtils.isNetworkAvailable()) {
            emit(emptyList())
        }
    }
    
    override fun searchExperiences(query: String): Flow<List<Experience>> = flow {
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = apiService.searchExperiences(query)
                if (response.isSuccessful) {
                    val experiences = response.body()?.data ?: emptyList()
                    emit(experiences)
                } else {
                    throw Exception("Search failed: error code ${response.code()}")
                }
            } catch (exception: Exception) {
                throw exception
            }
        } else {
            throw Exception("No network available for search")
        }
    }
    
    override fun getExperienceById(id: String): Flow<Experience> = flow {
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = apiService.getExperienceById(id)
                if (response.isSuccessful) {
                    val experience = response.body()?.data
                    if (experience != null) {
                        emit(experience)
                    } else {
                        throw Exception("Experience not found")
                    }
                } else {
                    throw Exception("Failed to fetch experience: error code ${response.code()}")
                }
            } catch (exception: Exception) {
                throw exception
            }
        } else {
            throw Exception("No network available to fetch experience details")
        }
    }
    
    override fun likeExperience(id: String): Flow<Int> = flow {
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = apiService.likeExperience(id)
                if (response.isSuccessful) {
                    val likesCount = response.body()?.likesCount
                    if (likesCount != null) {
                        localDataSource.updateLikeStatus(id, true, likesCount)
                        emit(likesCount)
                    } else {
                        throw Exception("Invalid like response")
                    }
                } else {
                    throw Exception("Failed to like experience: error code ${response.code()}")
                }
            }
            catch (exception: Exception) {
                throw exception
            }
        }else{
            throw Exception("No network available to like experience")
        }
    }
}
