package com.example.aplicativodeprevisodotempo.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicativodeprevisodotempo.data.local.dao.CityDao
import com.example.aplicativodeprevisodotempo.data.local.dao.WeatherDao
import com.example.aplicativodeprevisodotempo.data.local.model.CityEntity
import com.example.aplicativodeprevisodotempo.data.local.model.WeatherDetailEntity
import com.example.aplicativodeprevisodotempo.data.local.model.WeatherEntity
import com.example.aplicativodeprevisodotempo.data.local.model.WeatherInfoEntity

@Database(
    entities = [
        CityEntity::class,
        WeatherInfoEntity::class,
        WeatherDetailEntity::class,
        WeatherEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_db"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}