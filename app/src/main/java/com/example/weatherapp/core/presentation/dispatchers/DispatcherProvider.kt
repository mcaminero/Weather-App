package com.example.weatherapp.core.presentation.dispatchers

import kotlinx.coroutines.Dispatchers

class DispatcherProvider {

    val main
    get() = Dispatchers.Main

    val default
    get() = Dispatchers.Default

    val io
    get() = Dispatchers.IO

    val undefined
    get() = Dispatchers.Unconfined
}