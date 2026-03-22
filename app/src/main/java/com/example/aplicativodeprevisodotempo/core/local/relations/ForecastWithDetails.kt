package com.example.aplicativodeprevisodotempo.core.local.relations


import androidx.room.Embedded
import androidx.room.Relation
import com.example.aplicativodeprevisodotempo.core.local.model.WeatherDetailEntity
import com.example.aplicativodeprevisodotempo.core.local.model.WeatherInfoEntity

data class ForecastWithDetails(
    @Embedded val info: WeatherInfoEntity,
    @Relation(
        parentColumn = "parentCityName",
        entityColumn = "parentCityName"
    )
    val forecasts: List<WeatherDetailEntity>
)