package com.example.weatherapp.core.presentation.currentCity

import com.example.weatherapp.core.data.dto.WeatherResponse

data class CurrentCityUiState(
    val isLoading: Boolean = true,
    val currentCityData: WeatherResponse? = null,
    val error: String = ""
)