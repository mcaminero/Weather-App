package com.example.weatherapp.core.presentation.currentCity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.WeatherData
import com.example.weatherapp.core.domain.model.WeatherReport
import com.example.weatherapp.core.domain.weather.WeatherType
import com.example.weatherapp.core.presentation.navigation.MainNavigationGraph
import com.example.weatherapp.utils.BottomNavItems
import com.example.weatherapp.utils.items
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            MainNavigationGraph(
                navController = navController,
                scaffoldState = scaffoldState,
                modifier = Modifier
            )
            DefaultSnackBar(
                snackBarHostState = scaffoldState.snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }
}

@Composable
fun DefaultSnackBar(
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    SnackbarHost(
        hostState = snackBarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(text = data.message)
                },
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(onClick = onDismiss) {
                            Text(text = actionLabel)
                        }
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

@Composable
fun CurrentCityScreen(viewModel: CurrentCityViewModel) {
    val currentCity by viewModel.weatherReport

    currentCity?.let {
        Column {
            WeatherDataTodayScreen(
                modifier = Modifier,
                weatherReport = it
            )
            Spacer(Modifier.height(16.dp))
            WeatherForecast(
                modifier = Modifier,
                weatherReport = it
            )
        }
    }

}

@Composable
fun WeatherForecast(modifier: Modifier, weatherReport: WeatherReport) {
    weatherReport.weatherDataPerDay[0]?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Today",
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = modifier.height(16.dp))
            LazyRow(content = {
                items(data) { weatherData ->
                    HourlyWeatherDisplay(
                        weatherData,
                        Modifier
                            .height(100.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            })
        }
    }
}

@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    modifier: Modifier,
    textColor: Color = Color.Black
) {
    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            color = Color.Black
        )
        Image(
            painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = weatherData.weatherType.weatherDesc,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${weatherData.temperatureCelsius}°C",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun WeatherDataTodayScreen(modifier: Modifier, weatherReport: WeatherReport) {
    weatherReport.currentWeatherData?.apply {
        Column(
            modifier
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
                text = "${temperatureCelsius}°C",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = weatherType.weatherDesc,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "Wind speed: $windSpeed", fontWeight = FontWeight.Bold)
                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.wind_direction_arrow),
                        contentDescription = "$windDirection",
                        modifier = modifier.rotate(windDirection.toFloat() - 44),
                        tint = MaterialTheme.colors.onBackground
                    )
                    Text(text = "Wind direction", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun WeatherDataTodayScreenPrev() {
//    WeatherDataTodayScreen(
//        modifier = Modifier,
//        weatherReport = WeatherData(
//            LocalDateTime.now(),
//            0.0,
//            WeatherType.ClearSky,
//            180.0,
//            0.0
//        )
//    )
//}

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