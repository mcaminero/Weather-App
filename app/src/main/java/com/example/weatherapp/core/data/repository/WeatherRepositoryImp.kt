package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.data.mappers.toWeatherReport
import com.example.weatherapp.core.data.remote.WeatherApi
import com.example.weatherapp.core.domain.model.WeatherReport
import com.example.weatherapp.core.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(private val weatherApi: WeatherApi) :
    WeatherRepository {
        override suspend fun getWeather(lat: Double, long: Double): WeatherReport{
        return weatherApi.getWeather(lat, long).toWeatherReport()
    }
}