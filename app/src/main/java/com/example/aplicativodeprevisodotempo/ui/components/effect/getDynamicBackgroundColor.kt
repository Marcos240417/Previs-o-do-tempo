package com.example.aplicativodeprevisodotempo.ui.components.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.aplicativodeprevisodotempo.ui.theme.DayGradient
import com.example.aplicativodeprevisodotempo.ui.theme.NightGradient
import java.util.Calendar

@Composable
fun getDynamicBackgroundColor(): androidx.compose.ui.graphics.Brush {
    // O 'remember' evita que o app recalcule a hora a cada milissegundo
    val hour = remember { Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }

    // CORREÇÃO: "Two comparisons to range check"
    // Se a hora NÃO estiver entre 6h e 17h (ou seja, 18h até 05h), é Noite.
    val isNight = hour !in 6..17

    return if (isNight) NightGradient else DayGradient
}