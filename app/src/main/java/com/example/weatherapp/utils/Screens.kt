package com.example.weatherapp.utils

sealed class Screens(val route: String){
    object SplashScreen: Screens("splash_screen")
    object SearchScreen: Screens("seach_screen")
    object CurrentCityScreen: Screens("current_city_screen")
    object FavoriteCitiesScreen: Screens("favorite_cities_screen")
}

object Graph{
    const val RootGraph = "root_graph"
    const val SplashGraph = "splash_graph"
    const val MainGraph = "main_graph"
}