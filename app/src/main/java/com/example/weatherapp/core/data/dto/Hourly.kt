package com.example.weatherapp.core.data.dto

import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("temperature_2m")
    val temperatures: List<Double>,
    val time: List<String>,
    @SerializedName("weathercode")
    val weatherCode: List<Int>,
    @SerializedName("winddirection_10m")
    val windDirection: List<Double>,
    @SerializedName("windspeed_10m")
    val windSpeed: List<Double>
)