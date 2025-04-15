package com.yslvlln.feature.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CurrentWeatherScreen(
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        is CurrentWeatherUiState.Error -> {
            // TODO
        }

        is CurrentWeatherUiState.Success -> {
            val data = (state as CurrentWeatherUiState.Success).data.toUiModel()
            CurrentWeatherContent(data)
        }

        CurrentWeatherUiState.RequestingPermission -> {
            LocationPermissionRequester(
                onPermissionGranted = { viewModel.onPermissionUpdate(true) },
                onPermissionDenied = { viewModel.onPermissionUpdate(false) }
            )
        }

        CurrentWeatherUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        CurrentWeatherUiState.PermissionDenied -> {
            PermissionDeniedContent()
        }
    }
}

@Composable
internal fun CurrentWeatherContent(
    weather: CurrentWeatherUiModel,
    modifier: Modifier = Modifier
) {
    val currentHour = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .hour
    val isNightTime = currentHour >= 18 || currentHour < 6
    val formattedTime = getFormattedTime()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier.size(50.dp),
                painter = if (isNightTime) {
                    painterResource(R.drawable.ic_night)
                } else {
                    painterResource(R.drawable.ic_sunny)
                },
                contentDescription = null
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        if (weather.description.contains("raining", ignoreCase = true)) {
            Image(
                painter = painterResource(R.drawable.art_raining),
                contentDescription = weather.conditionTitle,
                contentScale = ContentScale.Fit
            )
        } else {
            Image(
                painter = painterResource(R.drawable.art_sunny),
                contentDescription = weather.conditionTitle,
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = weather.cityAndCountry,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(16.dp))

        SunriseSunsetRow(
            temp = weather.temperature,
            sunrise = weather.sunrise,
            sunset = weather.sunset
        )
    }
}

@Composable
fun SunriseSunsetRow(
    temp: String,
    sunrise: String,
    sunset: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WeatherInfoCard(
            icon = R.drawable.ic_thermometer,
            label = "Temp",
            time = temp,
            modifier = Modifier.weight(1f)
        )
        WeatherInfoCard(
            icon = R.drawable.ic_sunrise,
            label = "Sunrise",
            time = sunrise,
            modifier = Modifier.weight(1f)
        )
        WeatherInfoCard(
            icon = R.drawable.ic_sunset,
            label = "Sunset",
            time = sunset,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun WeatherInfoCard(
    @DrawableRes icon: Int,
    label: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = label,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light
            )

            Text(
                text = time,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
internal fun LocationPermissionRequester(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            onPermissionGranted()
        }
    }
}

@Composable
internal fun PermissionDeniedContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.art_waiting),
            contentDescription = "Location Off",
            alpha = 0.6f
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Enable location permission to see weather information for your area.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
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
    CurrentWeatherContent(
        weather = CurrentWeatherUiModel(
            conditionTitle = "Sunny",
            description = "",
            cityAndCountry = "Turin, IT",
            temperature = "24°C",
            sunrise = "06:23 AM",
            sunset = "07:58 PM"
        )
    )
}

@Preview(
    name = "Rainy Preview",
    showBackground = true
)
@Composable
internal fun RainyPreview() {
    CurrentWeatherContent(
        weather = CurrentWeatherUiModel(
            conditionTitle = "Rainy",
            description = "moderate rain",
            cityAndCountry = "Turin, IT",
            temperature = "17°C",
            sunrise = "06:23 AM",
            sunset = "07:58 PM"
        )
    )
}

@Preview(
    name = "Night Preview",
    showBackground = true
)
@Composable
internal fun NightPreview() {
    CurrentWeatherContent(
        weather = CurrentWeatherUiModel(
            conditionTitle = "Night",
            description = "",
            cityAndCountry = "Turin, IT",
            temperature = "12°C",
            sunrise = "06:23 AM",
            sunset = "07:58 PM"
        )
    )
}

