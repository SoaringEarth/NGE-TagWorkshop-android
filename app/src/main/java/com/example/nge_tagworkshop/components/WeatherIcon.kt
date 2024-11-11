package com.example.nge_tagworkshop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nge_tagworkshop.api.Weather
import com.example.nge_tagworkshop.api.WeatherData

@Composable
fun WeatherIcon(value: WeatherData) {
    Box(
        modifier = Modifier
            .background(
                color = Color.Blue.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = "${value.weather.roundedTemperature}°",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
            )
            AsyncImage(
                model = value.iconUrl,
                contentDescription = value.weather.description,
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Preview
@Composable
fun WeatherIconPreview() {
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
            .padding(all = 16.dp)
    ) {
        WeatherIcon(
            WeatherData(
                city = "London",
                country = "United Kingdom",
                weather = Weather(
                    temperature = 14.7,
                    icon = "10d",
                    description = "rainy and cloudy, typical london weather"
                )
            )
        )
    }
}