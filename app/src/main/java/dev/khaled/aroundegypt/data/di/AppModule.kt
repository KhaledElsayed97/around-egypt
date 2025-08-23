package dev.khaled.aroundegypt.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.khaled.aroundegypt.data.repository.ExperienceRepository
import dev.khaled.aroundegypt.data.repository.ExperienceRepositoryImpl
import dev.khaled.aroundegypt.data.service.ExperienceApiService
import dev.khaled.aroundegypt.data.usecases.GetExperienceByIdUseCase
import dev.khaled.aroundegypt.data.usecases.GetRecentExperiencesUseCase
import dev.khaled.aroundegypt.data.usecases.GetRecommendedExperiencesUseCase
import dev.khaled.aroundegypt.data.usecases.LikeExperienceUseCase
import dev.khaled.aroundegypt.data.usecases.SearchExperiencesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://aroundegypt.34ml.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideExperienceApiService(retrofit: Retrofit): ExperienceApiService {
        return retrofit.create(ExperienceApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideExperienceRepository(apiService: ExperienceApiService): ExperienceRepository {
        return ExperienceRepositoryImpl(apiService)
    }
    
    @Provides
    @Singleton
    fun provideGetRecommendedExperiencesUseCase(repository: ExperienceRepository): GetRecommendedExperiencesUseCase {
        return GetRecommendedExperiencesUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetRecentExperiencesUseCase(repository: ExperienceRepository): GetRecentExperiencesUseCase {
        return GetRecentExperiencesUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideSearchExperiencesUseCase(repository: ExperienceRepository): SearchExperiencesUseCase {
        return SearchExperiencesUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetExperienceByIdUseCase(repository: ExperienceRepository): GetExperienceByIdUseCase {
        return GetExperienceByIdUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideLikeExperienceUseCase(repository: ExperienceRepository): LikeExperienceUseCase {
        return LikeExperienceUseCase(repository)
    }
}
