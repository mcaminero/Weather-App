package com.example.weatherapp.core.presentation.currentCity

import com.example.weatherapp.core.domain.model.WeatherReport
data class CurrentCityUiState(
    val isLoading: Boolean = true,
    val currentCityData: WeatherReport? = null,
    val error: String = ""
)