package com.example.aplicativodeprevisodotempo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") val coord: CoordResponse,
    @SerializedName("name") val name: String,
    @SerializedName("main") val main: WeatherInfoResponse,
    @SerializedName("weather") val weatherDetails: List<WeatherDetailResponse>,
    @SerializedName("dt") val dateTimestamp: Long
)


