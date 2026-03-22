package com.example.aplicativodeprevisodotempo.core.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.example.aplicativodeprevisodotempo.core.data.DataStoreManager
import com.example.aplicativodeprevisodotempo.core.local.dao.CityDao
import com.example.aplicativodeprevisodotempo.core.local.dao.WeatherDao
import com.example.aplicativodeprevisodotempo.core.local.model.CityEntity
import com.example.aplicativodeprevisodotempo.core.local.relations.CityWithForecasts
import com.example.aplicativodeprevisodotempo.core.local.relations.ForecastWithDetails
import com.example.aplicativodeprevisodotempo.core.remote.api.WeatherService
import com.example.aplicativodeprevisodotempo.core.remote.mapper.toCityEntity
import com.example.aplicativodeprevisodotempo.core.remote.mapper.toWeatherInfoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val api: WeatherService,
    private val weatherDao: WeatherDao,
    private val cityDao: CityDao,
    private val dataStore: DataStoreManager
) : WeatherRepository {

    // --- CIDADES (DAO/ROOM) ---

    override fun getCitiesPagingSource(
        stateName: String,
        search: String
    ): PagingSource<Int, CityWithForecasts> {
        return cityDao.getCitiesByStatePaging(stateName, search)
    }

    override fun getStatesByRegion(region: String): Flow<List<String>> =
        cityDao.getStatesByRegion(region)


    override suspend fun updateCityListByState(state: String, cityNames: List<String>) {
        withContext(Dispatchers.IO) {
            try {
                val citiesToInsert = cityNames.map { name ->
                    CityEntity(
                        cityName = name,
                        state = state,
                        isFavorite = false,
                        lastUpdated = System.currentTimeMillis(),
                        latitude = "0.0",
                        longitude = "0.0",
                        region = "",
                        isFavoriteByGps = false
                    )
                }
                // Usamos updateCitiesForState para limpar as antigas e colocar as novas
                cityDao.updateCitiesForState(state, citiesToInsert)
                Log.d("Repository", "Cidades de $state atualizadas no banco.")
            } catch (e: Exception) {
                Log.e("WeatherRepository", "Erro ao popular banco: ${e.message}")
            }
        }
    }

    // --- CLIMA ATUAL & SINCRONIZAÇÃO ---

    override fun getCityWithForecasts(cityName: String): Flow<CityWithForecasts?> =
        weatherDao.getCityWithForecasts(cityName)

    override fun getCityWithForecastByCoords(
        lat: String, lon: String, cityName: String
    ): Flow<CityWithForecasts?> = weatherDao.getCityWithForecastByCoords(lat, lon, cityName)

    override suspend fun syncWeatherData(lat: String, lon: String, apiKey: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getWeather(lat, lon, apiKey)
                if (response.isSuccessful && response.body() != null) {
                    val weatherData = response.body()!!
                    weatherDao.insertFullWeather(
                        city = weatherData.toCityEntity(),
                        weatherInfo = weatherData.toWeatherInfoEntity()
                    )
                    dataStore.saveSyncInfo(weatherData.name, System.currentTimeMillis().toString())
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Erro API: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun syncWeatherByCityName(cityName: String, apiKey: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getWeatherByCityName(cityName, apiKey)
                if (response.isSuccessful && response.body() != null) {
                    val weatherData = response.body()!!
                    weatherDao.insertFullWeather(
                        city = weatherData.toCityEntity(),
                        weatherInfo = weatherData.toWeatherInfoEntity()
                    )
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Erro API: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // --- FAVORITOS & HISTÓRICO ---

    override fun getFavoriteCities(): Flow<List<CityWithForecasts>> =
        weatherDao.getFavoriteCitiesWithWeather()

    override suspend fun updateFavoriteStatus(cityName: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            cityDao.updateFavoriteStatus(cityName, isFavorite)
        }
    }

    override fun getForecastWithDetails(cityName: String): Flow<List<ForecastWithDetails>> =
        weatherDao.getForecastWithDetailsById(cityName)

    override fun getLastCitySynced(): Flow<String?> = dataStore.lastCityFlow

    override fun getAllCitiesWithWeather(): Flow<List<CityWithForecasts>> =
        weatherDao.getForecastsWithDetails()

    override fun getStaticLocalizations(): Map<String, String> = mapOf(
        "São Lourenço da Mata" to "-8.0011,-35.0181",
        "Recife" to "-8.0542,-34.8813",
        "Caruaru" to "-8.2845,-35.9698"
    )
}