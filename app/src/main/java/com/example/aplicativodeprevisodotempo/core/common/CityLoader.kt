package com.example.aplicativodeprevisodotempo.core.common

import android.content.Context
import android.util.Log
import org.json.JSONObject

object CityLoader {
    // Mapa aninhado: Região -> (Estado -> Lista de Cidades)
    private var fullData: Map<String, Map<String, List<String>>> = emptyMap()

    fun initialize(context: Context) {
        if (fullData.isNotEmpty()) return

        try {
            val jsonString = context.assets.open("cidades.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonObject = JSONObject(jsonString)
            val mutableRegions = mutableMapOf<String, Map<String, List<String>>>()

            val regions = jsonObject.keys()
            while (regions.hasNext()) {
                val regionName = regions.next()
                val statesObject = jsonObject.getJSONObject(regionName)
                val mutableStates = mutableMapOf<String, List<String>>()

                val states = statesObject.keys()
                while (states.hasNext()) {
                    val stateName = states.next()
                    val citiesArray = statesObject.getJSONArray(stateName)
                    val citiesList = mutableListOf<String>()

                    for (i in 0 until citiesArray.length()) {
                        citiesList.add(citiesArray.getString(i))
                    }
                    mutableStates[stateName] = citiesList.sorted()
                }
                mutableRegions[regionName] = mutableStates
            }
            fullData = mutableRegions
            Log.d("CityLoader", "Dados carregados com sucesso!")
        } catch (e: Exception) {
            Log.e("CityLoader", "Erro ao carregar JSON de cidades: ${e.message}")
        }
    }

    // Retorna todas as regiões disponíveis (Norte, Nordeste, etc.)
    fun getAvailableRegions(): List<String> {
        return fullData.keys.toList().sorted()
    }

    // Retorna a lista de estados de uma região
    fun getStatesForRegion(region: String): List<String> {
        return fullData[region]?.keys?.toList()?.sorted() ?: emptyList()
    }

    // Retorna a lista de cidades de um estado
    fun getCitiesForState(region: String, state: String): List<String> {
        return fullData[region]?.get(state) ?: emptyList()
    }
}