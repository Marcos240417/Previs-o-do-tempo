package com.example.aplicativodeprevisodotempo.core.data.repository

import androidx.paging.PagingSource
import com.example.aplicativodeprevisodotempo.core.local.relations.CityWithForecasts
import com.example.aplicativodeprevisodotempo.core.local.relations.ForecastWithDetails
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    // --- CIDADES (Brasil / CityLoader / Paging) ---
    fun getCitiesPagingSource(stateName: String, search: String): PagingSource<Int, CityWithForecasts>

    fun getStatesByRegion(region: String): Flow<List<String>>

    suspend fun updateCityListByState(state: String, cityNames: List<String>)

    // --- CLIMA ATUAL & API ---
    fun getCityWithForecasts(cityName: String): Flow<CityWithForecasts?>

    fun getCityWithForecastByCoords(lat: String, lon: String, cityName: String): Flow<CityWithForecasts?>

    suspend fun syncWeatherData(lat: String, lon: String, apiKey: String): Result<Unit>

    suspend fun syncWeatherByCityName(cityName: String, apiKey: String): Result<Unit>

    // --- FAVORITOS ---
    fun getFavoriteCities(): Flow<List<CityWithForecasts>>

    suspend fun updateFavoriteStatus(cityName: String, isFavorite: Boolean)

    // --- DETALHES & HISTÓRICO ---
    fun getForecastWithDetails(cityName: String): Flow<List<ForecastWithDetails>>

    fun getLastCitySynced(): Flow<String?>

    fun getStaticLocalizations(): Map<String, String>
    fun getAllCitiesWithWeather(): Flow<List<CityWithForecasts>>
}