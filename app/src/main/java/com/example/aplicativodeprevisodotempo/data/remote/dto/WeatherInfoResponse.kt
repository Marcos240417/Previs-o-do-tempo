package com.example.aplicativodeprevisodotempo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherInfoResponse(
    @SerializedName("temp") val temp: Float,
    @SerializedName("temp_min") val tempMin: Float,
    @SerializedName("temp_max") val tempMax: Float,
    @SerializedName("pressure") val pressure: Float,
    @SerializedName("humidity") val humidity: Float
)