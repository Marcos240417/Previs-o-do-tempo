package com.example.aplicativodeprevisodotempo.core.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.aplicativodeprevisodotempo.core.local.model.CityEntity
import com.example.aplicativodeprevisodotempo.core.local.relations.CityWithForecasts
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)

    @Query("DELETE FROM cities WHERE isFavorite = 0 AND state = :state")
    suspend fun deleteNonFavoritesByState(state: String)

    @Transaction
    suspend fun updateCitiesForState(state: String, cities: List<CityEntity>) {
        deleteNonFavoritesByState(state)
        insertCities(cities)
    }

    @Transaction
    @Query("SELECT * FROM cities WHERE state = :state AND cityName LIKE '%' || :search || '%' ORDER BY cityName ASC")
    fun getCitiesByStatePaging(state: String, search: String): PagingSource<Int, CityWithForecasts>

    @Query("SELECT DISTINCT state FROM cities WHERE region = :regionName")
    fun getStatesByRegion(regionName: String): Flow<List<String>>

    @Query("UPDATE cities SET isFavorite = :isFavorite WHERE cityName = :cityName")
    suspend fun updateFavoriteStatus(cityName: String, isFavorite: Boolean)
}