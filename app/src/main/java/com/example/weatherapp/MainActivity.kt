package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.core.presentation.navigation.RootNavigationGraph
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.utils.CheckPermissions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                CheckPermissions(
                    onPermissionDenied = {
                        finishAffinity()
                    },
                    onPermissionGranted = {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            RootNavigationGraph(
                                navController = rememberNavController(), modifier = Modifier
                            )
                        }
                    })
            }
        }
    }
}