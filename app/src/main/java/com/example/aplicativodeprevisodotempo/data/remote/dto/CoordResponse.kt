package com.example.aplicativodeprevisodotempo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoordResponse(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)
