package com.example.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.presentation.Screens
import com.example.weatherapp.presentation.splashScreen.SplashScreen
import com.example.weatherapp.presentation.mainScreen.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ){
        composable(Screens.SplashScreen.route){
            SplashScreen{
                navController.navigate(route = Screens.MainScreen.route)
            }
        }
        composable(Screens.MainScreen.route){
            MainScreen(name = "Hola Android!")
        }
    }
}