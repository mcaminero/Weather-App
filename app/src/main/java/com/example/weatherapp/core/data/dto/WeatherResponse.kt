package com.example.weatherapp.core.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val hourly: Hourly,
    @SerializedName("current_weather")
    val currentWeather: CurrentWeather
)