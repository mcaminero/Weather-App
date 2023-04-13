package com.example.weatherapp.core.domain.model

data class WeatherReport(
    val weatherDataPerDay: Map<Int,List<WeatherData>>,
    val currentWeatherData: WeatherData?
)
