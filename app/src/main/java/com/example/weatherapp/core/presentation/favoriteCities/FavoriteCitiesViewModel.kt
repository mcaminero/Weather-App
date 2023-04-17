package com.example.weatherapp.core.presentation.favoriteCities

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.presentation.baseUi.BaseUiState
import com.example.weatherapp.core.presentation.baseUi.BaseViewModel
import com.example.weatherapp.core.presentation.dispatchers.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCitiesViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    init {
        viewModelScope.launch {
            delay(3000)
            emit(BaseUiState(isLoading = false, hasError = false, error = null))
        }
    }
}
