package com.example.weatherapp.core.data.mappers

import com.example.weatherapp.core.data.dto.WeatherDataDto
import com.example.weatherapp.core.data.dto.WeatherResponse
import com.example.weatherapp.core.domain.model.WeatherData
import com.example.weatherapp.core.domain.model.WeatherReport
import com.example.weatherapp.core.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val windDirection = windDirections[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                weatherType = WeatherType.fromWMO(weatherCode),
                windSpeed = windSpeed,
                windDirection = windDirection
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { listEntry ->
        listEntry.value.map {
            it.data
        }
    }
}

fun WeatherResponse.toWeatherReport(): WeatherReport {
    val weatherDataMap = weatherDataDto.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.hour == 23) {
            if (now.minute < 30) now.hour else now.plusDays(1).hour + 1
        } else {
            if (now.minute < 30) now.hour else now.hour + 1
        }
        it.time.hour == hour
    }
    return WeatherReport(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}