package com.example.aplicativodeprevisodotempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.aplicativodeprevisodotempo.core.data.utils.CityLoader
import com.example.aplicativodeprevisodotempo.ui.navigation.WeatherNavGraph
import com.example.aplicativodeprevisodotempo.ui.theme.AplicativoDePrevisãoDoTempoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CityLoader.initialize(this)
        setContent {
            AplicativoDePrevisãoDoTempoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherNavGraph()
                }
            }
        }
    }
}