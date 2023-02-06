package com.example.weatherapp.core.presentation.currentCity

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.core.presentation.navigation.MainNavigationGraph
import com.example.weatherapp.utils.BottomNavItems
import com.example.weatherapp.utils.Screens
import com.example.weatherapp.utils.items
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    val isSplashScreen = currentRoute == Screens.SplashScreen.route
    Scaffold(bottomBar = {
        if (!isSplashScreen)
            BottomNavigationBar(
                items = items, currentRoute = currentRoute
            ){
                navController.navigate(it){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
    }) {
        MainNavigationGraph(navController = navController, modifier = Modifier.padding(it))
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CurrentCityScreen(navController: NavHostController) {
    val activity = LocalContext.current as Activity
    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission())
    { isGranted ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen", "PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }.apply {
        if (!permissions.allPermissionsGranted) {
            LaunchedEffect(key1 = true) {
                launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Current City Screen",
            modifier = Modifier.align(Alignment.Center)
        )
    }

    BackHandler {
        activity.finishAndRemoveTask()
    }
}

@Composable
fun BottomNavigationBar(items: List<BottomNavItems>,currentRoute: String?, onItemClick: (String) -> Unit) {
    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    onItemClick(item.route)
                },
                icon = {
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
    BottomNavigationBar(items,null) {}
}