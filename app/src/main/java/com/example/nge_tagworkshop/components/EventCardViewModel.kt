package com.example.nge_tagworkshop.components

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nge_tagworkshop.api.PexelRepository
import com.example.nge_tagworkshop.api.Photo
import com.example.nge_tagworkshop.api.WeatherData
import com.example.nge_tagworkshop.api.WeatherRepository
import com.example.nge_tagworkshop.models.Event
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

class EventCardViewModel(
    var event: Event,
    repository: PexelRepository = PexelRepository(),
    weatherRepository: WeatherRepository = WeatherRepository(),
): ViewModel() {

    var eventPhoto = mutableStateOf<Photo?>(null)
    var weatherData = mutableStateOf<WeatherData?>(null)

    init {
        viewModelScope.launch {
            getEventPhoto(repository)
            getWeather(weatherRepository)
        }
    }

    private suspend fun getWeather(weatherRepository: WeatherRepository) {
        weatherData.value = weatherRepository.getWeather(event.location)

    }

    private suspend fun getEventPhoto(repository: PexelRepository) {
        eventPhoto.value = repository.getPhoto(query = event.title)
    }

    fun parseCustomUtcString(): String {
        val parsedDate = ZonedDateTime.parse(event.time) // Parse the UTC string to ZonedDateTime
        val formatter = DateTimeFormatter.ofPattern("MMM-dd, yyyy") // Define the desired format
        return parsedDate.format(formatter) // Format the parsed date
    }

    fun getEventCost(): String {
        if (event.price == 0) {
            return "Free"
        } else {
            return "${getLocalCurrencySymbol()}${event.price}"
        }
    }

    private fun getLocalCurrencySymbol(locale: Locale = Locale.getDefault()): String {
        return Currency.getInstance(locale).symbol
    }
}