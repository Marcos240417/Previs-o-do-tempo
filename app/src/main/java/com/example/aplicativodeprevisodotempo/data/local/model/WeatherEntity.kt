package com.example.aplicativodeprevisodotempo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val cityName: String,
    val temp: Float,
    val humidity: Int
)

