package com.example.aplicativodeprevisodotempo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_details")
data class WeatherDetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val parentCityName: String,
    val description: String,
    val icon: String
)
