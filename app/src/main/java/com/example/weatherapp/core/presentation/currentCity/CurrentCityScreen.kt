package com.example.weatherapp.core.presentation.currentCity

import android.Manifest
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.core.presentation.navigation.MainNavigationGraph
import com.example.weatherapp.utils.BottomNavItems
import com.example.weatherapp.utils.items
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    //val isSplashScreen = currentRoute == Screens.SplashScreen.route

    Scaffold(bottomBar = {
        /*if (!isSplashScreen) */
        BottomNavigationBar(navController)
    }) {
        MainNavigationGraph(navController = navController, modifier = Modifier.padding(it))
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CurrentCityScreen(
    navController: NavHostController,
    viewModel: CurrentCityViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    val currentCity by viewModel.currentCityUiState.collectAsState()
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) { permissions ->
        if (permissions.values.any { !it }){
            activity.finishAffinity()
        }
    }
    if (permissionsState.allPermissionsGranted) {
            viewModel.getWeather()
        currentCity.let { cityUiState ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (cityUiState.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 4.dp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(cityUiState.currentCityData?.hourly!!.windSpeed) {
                            Text(
                                text = "wind speed: $it",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                }
            }
        }
    } else {
        LaunchedEffect(key1 = true) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }
    BackHandler {
        activity.finishAffinity()
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    BottomNavigationBar(
        items = items,
        currentRoute = currentRoute,
        onItemClick = {
            navController.navigate(it) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItems>,
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(selected = currentRoute == item.route, onClick = {
                onItemClick(item.route)
            }, icon = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(item.icon, contentDescription = "")
                    Text(text = item.name)
                }
            }
            )
        }
    }
}

@Preview()
@Composable
fun BottomNavigationBarPrev() {
    BottomNavigationBar(rememberNavController())
}