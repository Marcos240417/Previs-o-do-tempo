package com.example.aplicativodeprevisodotempo.ui.components.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.aplicativodeprevisodotempo.ui.theme.DayGradient
import com.example.aplicativodeprevisodotempo.ui.theme.NightGradient
import java.util.Calendar

@Composable
fun getDynamicBackgroundColor(): androidx.compose.ui.graphics.Brush {

    val hour = remember { Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }


    val isNight = hour !in 6..17

    return if (isNight) NightGradient else DayGradient
}