package com.example.aplicativodeprevisodotempo.data.remote.api

import com.example.aplicativodeprevisodotempo.core.common.Constants // Importe suas constantes
import com.example.aplicativodeprevisodotempo.data.remote.dto.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "pt_br"
    ): Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "pt_br"
    ): Response<WeatherResponse>
}