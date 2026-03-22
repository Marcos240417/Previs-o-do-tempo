package com.example.aplicativodeprevisodotempo.core.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherInfoResponse(
    @SerializedName("temp") val temp: Float,
    @SerializedName("temp_min") val tempMin: Float, // Adicionado
    @SerializedName("temp_max") val tempMax: Float, // Adicionado
    @SerializedName("pressure") val pressure: Float, // Adicionado
    @SerializedName("humidity") val humidity: Float // Alterado para Float para evitar mismatch
)