package com.example.nge_tagworkshop.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.math.roundToInt


data class WeatherData (
    val city: String,
    val country: String,
    val weather: Weather
) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"
}

data class Weather(
    val temperature: Double,
    val description: String,
    val icon: String)
{
    val roundedTemperature: Int
        get() = temperature.roundToInt()
}

class WeatherRepository {
    private var service: WeatherService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/SoaringEarth/EventsAPI/main/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(WeatherService::class.java)
    }

    suspend fun getWeather(): List<WeatherData> {
        val response = service.getWeather()
        return response
    }
}

interface WeatherService {

    @GET("mockWeather.json")
    suspend fun getWeather(): List<WeatherData>
}