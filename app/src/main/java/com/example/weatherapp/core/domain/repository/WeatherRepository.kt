package com.example.weatherapp.core.domain.repository

import com.example.weatherapp.core.domain.model.WeatherReport
import com.example.weatherapp.utils.NetResponse

interface WeatherRepository {
    suspend fun getWeather(lat: Double, long: Double): NetResponse<WeatherReport>
}