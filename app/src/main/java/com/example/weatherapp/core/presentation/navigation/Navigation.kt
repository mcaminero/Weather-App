package com.example.weatherapp.core.presentation.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.core.presentation.baseUi.BaseScreen
import com.example.weatherapp.core.presentation.currentCity.CurrentCityScreen
import com.example.weatherapp.core.presentation.currentCity.CurrentCityViewModel
import com.example.weatherapp.core.presentation.currentCity.MainScreen
import com.example.weatherapp.core.presentation.favoriteCities.FavoriteCitiesScreen
import com.example.weatherapp.core.presentation.favoriteCities.FavoriteCitiesViewModel
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
        composable(Graph.MainGraph) {
            MainScreen()
        }
    }
}

@Composable
fun MainNavigationGraph(
    modifier: Modifier,
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.MainGraph,
        startDestination = Screens.CurrentCityScreen.route,
        modifier = modifier
    ) {
        composable(Screens.CurrentCityScreen.route) {
            val activity = LocalContext.current as Activity
            val viewModel = hiltViewModel<CurrentCityViewModel>()
            BaseScreen(
                scaffoldState = scaffoldState,
                viewModel = viewModel,
                onError = { viewModel.getWeather() }
            ) {
                CurrentCityScreen(viewModel)
            }

            BackHandler {
                activity.finishAffinity()
            }
        }
        composable(Screens.FavoriteCitiesScreen.route) {
            val viewModel = hiltViewModel<FavoriteCitiesViewModel>()
            BaseScreen(
                scaffoldState = scaffoldState,
                viewModel = viewModel,
                onError = { }
            ) {
                FavoriteCitiesScreen(navController,viewModel)
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
