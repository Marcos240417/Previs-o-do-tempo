package com.example.aplicativodeprevisodotempo.core.remote.dto

import com.google.gson.annotations.SerializedName

data class CoordResponse(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)
