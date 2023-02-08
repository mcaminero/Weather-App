package com.example.weatherapp.core.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherDataDto(
    @SerializedName("temperature_2m")
    val temperatures: List<Double>,
    val time: List<String>,
    @SerializedName("weathercode")
    val weatherCodes: List<Int>,
    @SerializedName("winddirection_10m")
    val windDirections: List<Double>,
    @SerializedName("windspeed_10m")
    val windSpeeds: List<Double>
)