package com.example.weatherapp.utils

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermissions(
    onPermissionDenied: () -> Unit,
    onPermissionGranted: @Composable () -> Unit,
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) { permissions ->
        if (permissions.values.any { !it }) {
            onPermissionDenied()
        }
    }

    if (permissionsState.allPermissionsGranted) {
        onPermissionGranted()
    } else {
        LaunchedEffect(key1 = true) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

}