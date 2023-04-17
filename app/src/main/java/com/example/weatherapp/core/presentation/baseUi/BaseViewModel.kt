package com.example.weatherapp.core.presentation.baseUi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.presentation.dispatchers.DispatcherProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


abstract class BaseViewModel(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _baseUi = MutableLiveData(BaseUiState())
    val baseUi: LiveData<BaseUiState>
        get() = _baseUi


    fun <RESULT> safeLaunchAndHandleLoading(
        toRunFunction: suspend () -> RESULT,
        emitAfterSuccessToUi: (suspend (RESULT) -> Unit)? = null
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            emit(BaseUiState(isLoading = true))
            try {
                val result = toRunFunction()
                emit(BaseUiState(isLoading = false))
                withContext(dispatcherProvider.main) {
                    emitAfterSuccessToUi?.invoke(result)
                }
            } catch (ex: Exception) {
                emit(BaseUiState(isLoading = false, error = ex, hasError = true))
            }
        }
    }


    protected suspend fun emit(baseUiState: BaseUiState) = withContext(dispatcherProvider.main) {
        _baseUi.value = baseUiState
    }
}