package com.example.aplicativodeprevisodotempo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aplicativodeprevisodotempo.ui.theme.ElectricCyan
import com.example.aplicativodeprevisodotempo.ui.theme.HotPink
import com.example.aplicativodeprevisodotempo.ui.theme.NightCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCityBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        placeholder = { Text("Buscar cidade (ex: Caruaru)", color = Color.White.copy(alpha = 0.4f)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ElectricCyan) },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = NightCard,
            unfocusedContainerColor = NightCard,
            focusedBorderColor = HotPink, // Borda rosa ao clicar
            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
            cursorColor = HotPink,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}