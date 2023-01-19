package com.example.weatherapp.utils

sealed class Screens(val route: String){
    object SplashScreen: Screens("splash_screen")
    object MainScreen: Screens("main_screen")
}