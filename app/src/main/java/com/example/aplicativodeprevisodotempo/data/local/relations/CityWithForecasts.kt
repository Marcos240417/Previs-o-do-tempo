package com.example.aplicativodeprevisodotempo.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.aplicativodeprevisodotempo.data.local.model.CityEntity
import com.example.aplicativodeprevisodotempo.data.local.model.WeatherInfoEntity

data class CityWithForecasts(
    @Embedded val city: CityEntity,

    @Relation(
        parentColumn = "cityName",
        entityColumn = "parentCityName"
    )
    val forecasts: List<WeatherInfoEntity>
)