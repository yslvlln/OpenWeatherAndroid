package com.yslvlln.feature.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CurrentWeatherScreen(
    weather: CurrentWeatherUiModel,
    modifier: Modifier = Modifier
) {
    val formattedTime = getFormattedTime() // Get current time
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = formattedTime,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(weather.icon),
            contentDescription = weather.conditionTitle,
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "\uD83D\uDCCD ${weather.cityAndCountry}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = weather.temperature,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Sunrise: ${weather.sunrise}")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Sunset: ${weather.sunset}")
    }
}

@SuppressLint("NewApi")
private fun getFormattedTime(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return now.toJavaLocalDateTime().toLocalTime().format(formatter)
}

@Preview(
    name = "Sunny Preview",
    showBackground = true
)
@Composable
internal fun SunnyPreview() {
    CurrentWeatherScreen(
        weather = CurrentWeatherUiModel(
            conditionTitle = "Sunny",
            cityAndCountry = "Turin, IT",
            temperature = "24°C",
            sunrise = "06:23 AM",
            sunset = "07:58 PM",
            icon = R.drawable.art_sunny
        )
    )
}

@Preview(
    name = "Rainy Preview",
    showBackground = true
)
@Composable
internal fun RainyPreview() {
    CurrentWeatherScreen(
        weather = CurrentWeatherUiModel(
            conditionTitle = "Rainy",
            cityAndCountry = "Turin, IT",
            temperature = "17°C",
            sunrise = "06:23 AM",
            sunset = "07:58 PM",
            icon = R.drawable.art_raining
        )
    )
}

@Preview(
    name = "Night Preview",
    showBackground = true
)
@Composable
internal fun NightPreview() {
    CurrentWeatherScreen(
        weather = CurrentWeatherUiModel(
            conditionTitle = "Night",
            cityAndCountry = "Turin, IT",
            temperature = "12°C",
            sunrise = "06:23 AM",
            sunset = "07:58 PM",
            icon = R.drawable.art_night
        )
    )
}

