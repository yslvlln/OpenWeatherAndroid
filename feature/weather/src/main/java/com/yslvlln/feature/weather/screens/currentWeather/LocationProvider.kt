package com.yslvlln.feature.weather.screens.currentWeather

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation() = suspendCancellableCoroutine { cont ->
        val cancellationToken = CancellationTokenSource()

        fusedClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationToken.token
        ).addOnSuccessListener { location ->
            cont.resume(location)
        }.addOnFailureListener {
            cont.resume(null)
        }

        cont.invokeOnCancellation {
            cancellationToken.cancel()
        }
    }
}
