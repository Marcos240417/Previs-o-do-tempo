package com.example.aplicativodeprevisodotempo.core.data.utils

import com.example.aplicativodeprevisodotempo.BuildConfig
import com.example.aplicativodeprevisodotempo.R

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = BuildConfig.API_KEY

    fun getWeatherIcon(iconCode: String?): Int {
        return when (iconCode) {
            "01d" -> R.drawable.weather_icon_01d // Céu limpo (Dia)
            "01n" -> R.drawable.weather_icon_01n // Céu limpo (Noite)
            "02d" -> R.drawable.weather_icon_02d // Poucas nuvens (Dia)
            "02n" -> R.drawable.weather_icon_02n // Poucas nuvens (Noite)
            "03d" -> R.drawable.weather_icon_03dd // Nuvens dispersas (Dia)
            "03n" -> R.drawable.weather_icon_03n  // Nuvens dispersas (Noite)
            "04d" -> R.drawable.weather_icon_04d  // Nuvens quebradas (Dia)
            "04n" -> R.drawable.weather_icon_04n  // Nuvens quebradas (Noite)
            "09d" -> R.drawable.weather_icon_09d  // Chuva rápida/garoa (Dia)
            "09n" -> R.drawable.weather_icon_09n  // Chuva rápida/garoa (Noite)
            "10d" -> R.drawable.weather_icon_10d  // Chuva (Dia)
            "10n" -> R.drawable.weather_icon_10n  // Chuva (Noite)
            "11d" -> R.drawable.weather_icon_11d  // Tempestade (Dia)
            "11n" -> R.drawable.weather_icon_11n  // Tempestade (Noite)
            "50d" -> R.drawable.weather_icon_50d  // Névoa/Neblina (Dia)
            "50n" -> R.drawable.weather_icon_50n  // Névoa/Neblina (Noite)

            else -> R.drawable.weather_icon_03dd
        }
    }
}