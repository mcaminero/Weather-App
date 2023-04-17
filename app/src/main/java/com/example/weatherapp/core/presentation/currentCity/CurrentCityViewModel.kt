package com.example.weatherapp.core.presentation.currentCity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.domain.location.LocationTracker
import com.example.weatherapp.core.domain.model.WeatherReport
import com.example.weatherapp.core.domain.repository.WeatherRepository
import com.example.weatherapp.core.presentation.baseUi.BaseViewModel
import com.example.weatherapp.core.presentation.dispatchers.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _weatherReport = mutableStateOf<WeatherReport?>(null)
    val weatherReport: State<WeatherReport?>
        get() = _weatherReport

    init {
        getWeather()
    }

    fun getWeather() = viewModelScope.launch {
        locationTracker.getCurrentLocation()?.let { location ->
            safeLaunchAndHandleLoading(
                toRunFunction = {
                    weatherRepository.getWeather(
                        location.latitude,
                        location.longitude
                    )
                },
                emitAfterSuccessToUi = {
                    _weatherReport.value = it
                }
            )
        }
    }
}