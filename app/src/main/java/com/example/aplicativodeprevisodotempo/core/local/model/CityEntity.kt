package com.example.aplicativodeprevisodotempo.core.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// CityEntity.kt
@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey
    val cityName: String, // Ex: "São Lourenço da Mata"
    val latitude: String,  // ADICIONADO
    val longitude: String, // ADICIONADO
    val lastUpdated: Long,
    val state: String = "",
    val region: String,
    val isFavorite: Boolean,
    val isFavoriteByGps: Boolean
)

// WeatherInfoEntity.kt
@Entity(
    tableName = "weather_info",
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["cityName"],
            childColumns = ["parentCityName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["parentCityName"])]
)
data class WeatherInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val parentCityName: String,
    val temp: Float,        // Clima Atual
    val tempMin: Float,     // Mínima do momento
    val tempMax: Float,     // Máxima do momento
    val humidity: Int,      // Umidade
    val pressure: Float,    // Pressão
    val description: String,// Ex: "Céu limpo"
    val icon: String,       // Ícone da API
    val timestamp: Long     // Hora da última atualização
)





