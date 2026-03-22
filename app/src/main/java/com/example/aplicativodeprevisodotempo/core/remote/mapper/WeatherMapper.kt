package com.example.aplicativodeprevisodotempo.core.remote.mapper

import com.example.aplicativodeprevisodotempo.core.local.model.CityEntity
import com.example.aplicativodeprevisodotempo.core.local.model.WeatherInfoEntity
import com.example.aplicativodeprevisodotempo.core.remote.dto.WeatherResponse

fun WeatherResponse.toEntities(
    stateName: String = "Pernambuco",
    regionName: String = "Nordeste"
): Pair<CityEntity, WeatherInfoEntity> {

    val city = CityEntity(
        cityName = this.name,
        latitude = this.coord.lat.toString(),
        longitude = this.coord.lon.toString(),
        lastUpdated = System.currentTimeMillis(),
        state = stateName,
        region = regionName,
        isFavorite = false,
        isFavoriteByGps = false
    )

    val weatherDetail = this.weatherDetails.firstOrNull()

    val info = WeatherInfoEntity(
        parentCityName = this.name,
        temp = this.main.temp,
        tempMin = this.main.tempMin,
        tempMax = this.main.tempMax,
        humidity = this.main.humidity.toInt(),
        pressure = this.main.pressure,
        timestamp = this.dateTimestamp,
        description = weatherDetail?.description ?: "Sem descrição",
        icon = weatherDetail?.icon ?: "01d"
    )
    return Pair(city, info)
}

fun WeatherResponse.toCityEntity(): CityEntity = this.toEntities().first
fun WeatherResponse.toWeatherInfoEntity(): WeatherInfoEntity = this.toEntities().second