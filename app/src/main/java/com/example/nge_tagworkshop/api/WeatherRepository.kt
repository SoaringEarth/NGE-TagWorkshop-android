package com.example.nge_tagworkshop.api

import com.example.nge_tagworkshop.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class WeatherData (
    val temperature: Double,
    val description: String,
    val icon: String
)

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
)

data class Weather(
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double?,
)

class WeatherRepository {
    private var service: WeatherService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(WeatherService::class.java)
    }

    suspend fun getWeather(city: String): WeatherData? {
        val response = service.searchForWeather(city)

        if (response.weather.isEmpty() || response.main.temp == null) {
            return null
        }

        val weatherData = WeatherData(
            temperature = response.main.temp,
            description = response.weather[0].description,
            icon = "https://openweathermap.org/img/wn/${response.weather[0].icon}@2x.png"
        )

        return weatherData
    }
}

interface WeatherService {

    @GET("weather")
    suspend fun searchForWeather(
        @Query("q") city: String,
        @Query("units") weatherUnit: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): WeatherResponse
}