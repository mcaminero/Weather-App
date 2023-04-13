package com.example.weatherapp.core.presentation.baseUi

data class BaseUiState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val error: Exception? = null
)