package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.data.mappers.toWeatherReport
import com.example.weatherapp.core.data.remote.WeatherApi
import com.example.weatherapp.core.domain.model.WeatherReport
import com.example.weatherapp.core.domain.repository.WeatherRepository
import com.example.weatherapp.utils.NetResponse
import retrofit2.awaitResponse
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(private val weatherApi: WeatherApi) :
    WeatherRepository {
    override suspend fun getWeather(lat: Double, long: Double): NetResponse<WeatherReport> {
            val response = weatherApi.getWeather(lat,long).awaitResponse()
        return try {
            NetResponse.Success(
                data = response.body()?.toWeatherReport()
            )

        } catch (e: Exception) {
            NetResponse.Error(response.message())
        }
    }
}