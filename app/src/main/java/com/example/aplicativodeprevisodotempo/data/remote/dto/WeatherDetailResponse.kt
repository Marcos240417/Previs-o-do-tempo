package com.example.aplicativodeprevisodotempo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherDetailResponse(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)