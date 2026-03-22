package com.example.aplicativodeprevisodotempo.core.remote.mapper

import com.example.aplicativodeprevisodotempo.core.local.model.CityEntity
import com.example.aplicativodeprevisodotempo.core.local.model.WeatherInfoEntity
import com.example.aplicativodeprevisodotempo.core.remote.dto.WeatherResponse

/**
 * Converte o DTO da API (WeatherResponse) em um par de Entidades do Room.
 */
fun WeatherResponse.toEntities(
    stateName: String = "Pernambuco",
    regionName: String = "Nordeste"
): Pair<CityEntity, WeatherInfoEntity> {

    // Exemplo de como deve ficar a criação do objeto CityEntity
    val city = CityEntity(
        cityName = this.name, // Nome que vem da API (Ex: Recife)
        latitude = this.coord.lat.toString(),  // VALOR ADICIONADO
        longitude = this.coord.lon.toString(), // VALOR ADICIONADO
        lastUpdated = System.currentTimeMillis(),
        state = stateName,
        region = regionName,
        isFavorite = false,
        isFavoriteByGps = false
    )

    // Usamos a variável aqui para evitar múltiplas chamadas ao firstOrNull()
    val weatherDetail = this.weatherDetails.firstOrNull()

    val info = WeatherInfoEntity(
        parentCityName = this.name,
        temp = this.main.temp,
        tempMin = this.main.tempMin,
        tempMax = this.main.tempMax,
        // Certifique-se de que na sua Entity 'humidity' é Int. Se for, use .toInt()
        humidity = this.main.humidity.toInt(),
        pressure = this.main.pressure,
        timestamp = this.dateTimestamp,
        // AGORA USAMOS A VARIÁVEL: O aviso desaparece aqui!
        description = weatherDetail?.description ?: "Sem descrição",
        icon = weatherDetail?.icon ?: "01d"
    )

    return Pair(city, info)
}

fun WeatherResponse.toCityEntity(): CityEntity = this.toEntities().first

fun WeatherResponse.toWeatherInfoEntity(): WeatherInfoEntity = this.toEntities().second