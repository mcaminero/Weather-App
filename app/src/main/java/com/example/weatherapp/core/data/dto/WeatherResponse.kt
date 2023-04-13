package com.example.weatherapp.core.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("hourly")
    val weatherDataDto: WeatherDataDto
)