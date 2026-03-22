package com.example.aplicativodeprevisodotempo.core.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val cityName: String,
    val temp: Float,
    val humidity: Int
)

