package dev.khaled.aroundegypt.data.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.khaled.aroundegypt.data.local.AppDatabase
import dev.khaled.aroundegypt.data.local.room.dao.ExperienceDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "around_egypt_database"
        )
        .fallbackToDestructiveMigration()
        .build()
        return database
    }
    
    @Provides
    @Singleton
    fun provideExperienceDao(database: AppDatabase): ExperienceDao {
        return database.experienceDao()
    }
}
