package com.example.weatherapp.core.domain.repository

import com.example.weatherapp.core.domain.model.WeatherReport

interface WeatherRepository {
    suspend fun getWeather(lat: Double, long: Double): WeatherReport?
}