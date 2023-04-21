package com.example.weatherapp.core.presentation.favoriteCities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FavoriteCitiesScreen(navController: NavHostController, viewModel: FavoriteCitiesViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorite Cities Screen",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}