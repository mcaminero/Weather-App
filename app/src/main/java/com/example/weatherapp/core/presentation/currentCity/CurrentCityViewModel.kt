package com.example.weatherapp.core.presentation.currentCity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.domain.location.LocationTracker
import com.example.weatherapp.core.domain.repository.WeatherRepository
import com.example.weatherapp.utils.NetResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
) :
    ViewModel() {

    private val _currentCityUiState = MutableStateFlow(CurrentCityUiState())
    val currentCityUiState: StateFlow<CurrentCityUiState>
        get() = _currentCityUiState


    fun getWeather() = viewModelScope.launch {
        locationTracker.getCurrentLocation()?.let { location ->
            _currentCityUiState.value =
                when (val response = weatherRepository.getWeather(location.latitude, location.longitude)) {
                    is NetResponse.Error -> CurrentCityUiState(false, null, response.message!!)
                    is NetResponse.Loading -> CurrentCityUiState(true, null)
                    is NetResponse.Success -> CurrentCityUiState(false, response.data)
                }
        }
    }
}