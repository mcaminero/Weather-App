package com.example.weatherapp.utils

sealed class NetResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): NetResponse<T>(data)
    class Error<T>(message: String, data: T?= null): NetResponse<T>(data, message)
    class Loading<T>(val isLoading: Boolean): NetResponse<T>()
}
