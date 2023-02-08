package com.example.weatherapp.core.presentation.modules

import android.app.Application
import com.example.weatherapp.core.data.Routes
import com.example.weatherapp.core.data.location.DefaultLocationTracker
import com.example.weatherapp.core.data.remote.WeatherApi
import com.example.weatherapp.core.data.repository.WeatherRepositoryImp
import com.example.weatherapp.core.domain.location.LocationTracker
import com.example.weatherapp.core.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val logger = HttpLoggingInterceptor().setLevel(Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(logger)

    @Provides
    @Singleton
    fun provideApi(): WeatherApi =
        Retrofit.Builder()
            .baseUrl(Routes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(weatherApi: WeatherApi): WeatherRepository =
        WeatherRepositoryImp(weatherApi)

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(app: Application): FusedLocationProviderClient{
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun providesLocationTracker(client: FusedLocationProviderClient,app: Application): LocationTracker{
        return DefaultLocationTracker(client,app)
    }
}