@file:Suppress("DEPRECATION")

package com.example.aplicativodeprevisodotempo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import java.util.Calendar

// Esquema para o MODO NOITE (Usa tons escuros da sua paleta)
private val DarkColorScheme = darkColorScheme(
    primary = HotPink,         // Rosa Choque para botões e ações principais
    secondary = ElectricCyan,  // Ciano para ícones e dados técnicos
    tertiary = SoftWhite,
    background = NightSkyTop,  // Fundo azul quase preto
    surface = NightSkyBottom,  // Superfícies em azul royal escuro
    onPrimary = Color.White,
    onSecondary = NightSkyTop, // Texto escuro sobre o ciano para contraste
    onBackground = Color.White,
    onSurface = Color.White
)

// Esquema para o MODO DIA (Usa tons mais claros e vivos)
private val LightColorScheme = lightColorScheme(
    primary = HotPink,
    secondary = ElectricCyan,
    tertiary = DaySkyTop,
    background = SoftWhite,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color(0xFF00363A), // Um tom de ciano escuro para legibilidade
    onBackground = DaySkyBottom,
    onSurface = DaySkyBottom
)

@Composable
fun AplicativoDePrevisãoDoTempoTheme(
    // Lógica automática: Se for entre 18h e 06h, força o tema escuro
    darkTheme: Boolean = isSystemInDarkTheme() || (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) !in 6..17),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    // Sincronização da barra de status e navegação do Android
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Faz a barra de status ter a mesma cor do fundo do App
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()

            val controller = WindowCompat.getInsetsController(window, view)
            // Ícones claros na noite, escuros no dia
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Certifique-se de que o arquivo Typography.kt existe
        content = content
    )
}