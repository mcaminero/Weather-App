package com.example.weatherapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.utils.Screens
import com.example.weatherapp.core.presentation.splashScreen.SplashScreen
import com.example.weatherapp.core.presentation.mainScreen.MainScreen

@Composable
fun AppNavigation(navController: NavHostController,modifier: Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ){
        composable(Screens.SplashScreen.route){
            SplashScreen{
                navController.navigate(route = Screens.MainScreen.route)
            }
        }
        composable(Screens.MainScreen.route){
            MainScreen(navController)
        }
    }
}