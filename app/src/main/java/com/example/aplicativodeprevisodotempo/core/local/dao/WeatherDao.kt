package com.example.aplicativodeprevisodotempo.core.local.dao

import androidx.room.*
import com.example.aplicativodeprevisodotempo.core.local.model.CityEntity
import com.example.aplicativodeprevisodotempo.core.local.model.WeatherInfoEntity
import com.example.aplicativodeprevisodotempo.core.local.relations.CityWithForecasts
import com.example.aplicativodeprevisodotempo.core.local.relations.ForecastWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(weatherInfo: WeatherInfoEntity)

    @Transaction
    suspend fun insertFullWeather(city: CityEntity, weatherInfo: WeatherInfoEntity) {
        insertCity(city)
        insertWeatherInfo(weatherInfo)
    }

    @Transaction
    @Query("""
        SELECT * FROM cities 
        WHERE cityName = :cityName 
        OR (latitude LIKE :lat || '%' AND longitude LIKE :lon || '%') 
        LIMIT 1
    """)
    fun getCityWithForecastByCoords(lat: String, lon: String, cityName: String): Flow<CityWithForecasts?>

    @Transaction
    @Query("SELECT * FROM cities WHERE cityName = :name")
    fun getCityWithForecasts(name: String): Flow<CityWithForecasts?>

    @Transaction
    @Query("SELECT * FROM cities")
    fun getForecastsWithDetails(): Flow<List<CityWithForecasts>>

    @Transaction
    @Query("SELECT * FROM cities WHERE isFavorite = 1")
    fun getFavoriteCitiesWithWeather(): Flow<List<CityWithForecasts>>

    @Transaction
    @Query("SELECT * FROM weather_info WHERE parentCityName = :cityId")
    fun getForecastWithDetailsById(cityId: String): Flow<List<ForecastWithDetails>>
}