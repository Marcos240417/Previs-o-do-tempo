package com.example.aplicativodeprevisodotempo.core.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.aplicativodeprevisodotempo.core.local.model.CityEntity
import com.example.aplicativodeprevisodotempo.core.local.model.WeatherInfoEntity

data class CityWithForecasts(
    @Embedded val city: CityEntity,

    @Relation(
        parentColumn = "cityName",
        entityColumn = "parentCityName"
    )
    val forecasts: List<WeatherInfoEntity>
)