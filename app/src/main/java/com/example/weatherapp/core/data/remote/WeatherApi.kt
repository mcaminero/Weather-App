package com.example.weatherapp.core.data.remote

import com.example.weatherapp.core.data.dto.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?&hourly=temperature_2m,weathercode,windspeed_10m,winddirection_10m")
    fun getWeather(@Query("latitude") lat: Double, @Query("longitude") long: Double): Call<WeatherResponse>
}