package com.example.aplicativodeprevisodotempo.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "weather_settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val LAST_CITY_KEY = stringPreferencesKey("last_city_synced")
        val LAST_SYNC_TIME = stringPreferencesKey("last_sync_time")
    }

    suspend fun saveSyncInfo(cityName: String, timestamp: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_CITY_KEY] = cityName
            prefs[LAST_SYNC_TIME] = timestamp
        }
    }

    val lastCityFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[LAST_CITY_KEY]
    }
}