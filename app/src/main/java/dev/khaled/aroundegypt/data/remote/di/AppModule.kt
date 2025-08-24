package dev.khaled.aroundegypt.data.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.khaled.aroundegypt.data.local.datasource.ExperienceLocalDataSource
import dev.khaled.aroundegypt.domain.repository.ExperienceRepository
import dev.khaled.aroundegypt.data.remote.repository.ExperienceRepositoryImpl
import dev.khaled.aroundegypt.data.remote.service.ExperienceApiService
import dev.khaled.aroundegypt.utils.NetworkUtils
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
    fun provideExperienceRepository(
        apiService: ExperienceApiService,
        localDataSource: ExperienceLocalDataSource,
        networkUtils: NetworkUtils
    ): ExperienceRepository {
        return ExperienceRepositoryImpl(apiService, localDataSource, networkUtils)
    }
}
