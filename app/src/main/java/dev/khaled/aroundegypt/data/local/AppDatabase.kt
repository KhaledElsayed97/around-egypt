package dev.khaled.aroundegypt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.khaled.aroundegypt.data.local.room.dao.ExperienceDao
import dev.khaled.aroundegypt.data.local.room.entity.RecentExperienceEntity
import dev.khaled.aroundegypt.data.local.room.entity.RecommendedExperienceEntity

@Database(
    entities = [
        RecommendedExperienceEntity::class,
        RecentExperienceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun experienceDao(): ExperienceDao
}
