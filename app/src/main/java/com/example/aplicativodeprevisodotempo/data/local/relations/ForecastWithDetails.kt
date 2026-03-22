package com.example.aplicativodeprevisodotempo.data.local.relations


import androidx.room.Embedded
import androidx.room.Relation
import com.example.aplicativodeprevisodotempo.data.local.model.WeatherDetailEntity
import com.example.aplicativodeprevisodotempo.data.local.model.WeatherInfoEntity

data class ForecastWithDetails(
    @Embedded val info: WeatherInfoEntity,
    @Relation(
        parentColumn = "parentCityName",
        entityColumn = "parentCityName"
    )
    val forecasts: List<WeatherDetailEntity>
)