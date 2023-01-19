package com.example.weatherapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItems(
    val name: String,
    val icon: ImageVector,
    val route: String
) {
    class SearchItem(
        name: String = "Search",
        icon: ImageVector = Icons.Default.Search,
        route: String = Screens.SearchScreen.route
    ) : BottomNavItems(name, icon, route)

    class CurrentCityItem(
        name: String = "Current City",
        icon: ImageVector = Icons.Default.LocationOn,
        route: String = Screens.CurrentCityScreen.route
    ) : BottomNavItems(name, icon, route)

    class FavoriteCitiesItem(
        name: String = "Favorite Cities",
        icon: ImageVector = Icons.Default.List,
        route: String = Screens.FavoriteCitiesScreen.route
    ) : BottomNavItems(name, icon, route)
}

val items = listOf(
    BottomNavItems.SearchItem(),
    BottomNavItems.CurrentCityItem(),
    BottomNavItems.FavoriteCitiesItem()
)