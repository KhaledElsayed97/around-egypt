package dev.khaled.aroundegypt.data.remote.service

import dev.khaled.aroundegypt.data.remote.model.ExperienceResponse
import dev.khaled.aroundegypt.data.remote.model.LikeResponse
import dev.khaled.aroundegypt.data.remote.model.SingleExperienceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExperienceApiService {
    
    @GET("api/v2/experiences")
    suspend fun getRecommendedExperiences(
        @Query("filter[recommended]") recommended: Boolean = true
    ): Response<ExperienceResponse>
    
    @GET("api/v2/experiences")
    suspend fun getRecentExperiences(): Response<ExperienceResponse>
    
    @GET("api/v2/experiences")
    suspend fun searchExperiences(
        @Query("filter[title]") searchText: String
    ): Response<ExperienceResponse>
    
    @GET("api/v2/experiences/{id}")
    suspend fun getExperienceById(
        @Path("id") id: String
    ): Response<SingleExperienceResponse>
    
    @POST("api/v2/experiences/{id}/like")
    suspend fun likeExperience(
        @Path("id") id: String
    ): Response<LikeResponse>
}
