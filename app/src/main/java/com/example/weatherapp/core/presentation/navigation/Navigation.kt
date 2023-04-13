package com.example.weatherapp.core.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.core.presentation.currentCity.CurrentCityScreen
import com.example.weatherapp.core.presentation.currentCity.MainScreen
import com.example.weatherapp.core.presentation.splash.SplashScreen
import com.example.weatherapp.utils.Graph
import com.example.weatherapp.utils.Screens

@Composable
fun RootNavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.RootGraph,
        startDestination = Screens.SplashScreen.route
    ) {
        composable(Screens.SplashScreen.route) {
            SplashScreen {
                navController.navigate(Graph.MainGraph)
            }
        }
        composable(Graph.MainGraph){
            MainScreen()
        }
    }
}

@Composable
fun MainNavigationGraph(modifier: Modifier,navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.MainGraph,
        startDestination = Screens.CurrentCityScreen.route,
        modifier = modifier
    ) {
        composable(Screens.CurrentCityScreen.route) {
            CurrentCityScreen(navController)
        }
        composable(Screens.FavoriteCitiesScreen.route) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Favorite Cities Screen",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        composable(Screens.SearchScreen.route) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Search Screen",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
