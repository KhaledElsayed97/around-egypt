package dev.khaled.aroundegypt.data.local.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.khaled.aroundegypt.data.local.room.dao.ExperienceDao
import dev.khaled.aroundegypt.data.local.datasource.ExperienceLocalDataSource
import dev.khaled.aroundegypt.data.local.datasource.ExperienceLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    
    @Provides
    @Singleton
    fun provideExperienceLocalDataSource(experienceDao: ExperienceDao): ExperienceLocalDataSource {
        return ExperienceLocalDataSourceImpl(experienceDao)
    }
}
