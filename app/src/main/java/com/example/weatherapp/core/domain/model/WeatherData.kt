package com.example.weatherapp.core.domain.model

import com.example.weatherapp.core.domain.weather.WeatherType
import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val weatherType: WeatherType,
    val windDirection: Double,
    val windSpeed: Double
)
