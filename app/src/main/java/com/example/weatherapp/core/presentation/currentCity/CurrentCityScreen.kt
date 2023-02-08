package com.example.weatherapp.core.presentation.currentCity

import android.Manifest
import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.WeatherData
import com.example.weatherapp.core.domain.weather.WeatherType
import com.example.weatherapp.core.presentation.navigation.MainNavigationGraph
import com.example.weatherapp.utils.BottomNavItems
import com.example.weatherapp.utils.items
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
    navController: NavHostController, viewModel: CurrentCityViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    val currentCity by viewModel.currentCityUiState.collectAsState()
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) { permissions ->
        if (permissions.values.any { !it }) {
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
                    WeatherDataTodayScreen(
                        modifier = Modifier,
                        weatherData = currentCity.currentCityData?.currentWeatherData
                    )
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
fun WeatherDataTodayScreen(modifier: Modifier, weatherData: WeatherData?) {
    weatherData?.apply {
        Column(
            modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "${time.dayOfWeek} ${time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                textAlign = TextAlign.End
            )
            Spacer(modifier = modifier.height(16.dp))
            Image(
                modifier = modifier
                    .size(200.dp)
                    .align(CenterHorizontally),
                painter = painterResource(id = weatherType.iconRes),
                contentDescription = weatherType.weatherDesc,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = weatherType.weatherDesc,
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(text = "wind speed: $windSpeed")
                }
                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.wind_direction_arrow),
                        contentDescription = "$windDirection",
                        modifier = modifier.rotate(windDirection.toFloat() - 44),
                        tint = MaterialTheme.colors.onBackground
                    )
                    Text(text = "wind direction")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherDataTodayScreenPrev() {
    WeatherDataTodayScreen(
        modifier = Modifier,
        weatherData = WeatherData(
            LocalDateTime.now(),
            0.0,
            WeatherType.ClearSky,
            180.0,
            0.0
        )
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    BottomNavigationBar(items = items, currentRoute = currentRoute, onItemClick = {
        navController.navigate(it) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    })
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItems>, currentRoute: String?, onItemClick: (String) -> Unit
) {
    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(selected = currentRoute == item.route, onClick = {
                onItemClick(item.route)
            }, icon = {
                Column(horizontalAlignment = CenterHorizontally) {
                    Icon(item.icon, contentDescription = "")
                    Text(text = item.name)
                }
            })
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPrev() {
    BottomNavigationBar(rememberNavController())
}