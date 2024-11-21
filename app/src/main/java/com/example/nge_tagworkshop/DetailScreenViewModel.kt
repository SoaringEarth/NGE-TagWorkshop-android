package com.example.nge_tagworkshop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nge_tagworkshop.api.EventsRepository
import com.example.nge_tagworkshop.api.PexelRepository
import com.example.nge_tagworkshop.api.Photo
import com.example.nge_tagworkshop.api.Weather
import com.example.nge_tagworkshop.api.WeatherRepository
import com.example.nge_tagworkshop.models.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

class DetailScreenViewModel(
    var eventId: Int,
    private var eventsRepository: EventsRepository = EventsRepository(),
    private var weatherRepository: WeatherRepository = WeatherRepository(),
    private var photoRepository: PexelRepository = PexelRepository()
): ViewModel() {

    var weather: Weather? by mutableStateOf(null)

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    private val _eventPhoto = MutableStateFlow<Photo?>(null)
    val eventPhoto: StateFlow<Photo?> = _eventPhoto

    suspend fun fetchEventData(eventID: String) {
        viewModelScope.launch {

            val fetchedEvent = eventsRepository.getEvent(eventId)

            fetchedEvent?.title?.let { title ->
                _event.value = fetchedEvent
                _eventPhoto.value = photoRepository.getPhoto(query = title)

                fetchedEvent.location.let { location ->
                    val allWeather = weatherRepository.getWeather()
                    println(allWeather)

                    weather = allWeather.find { location.contains(it.city) }
                    println(weather)
                }
            }
        }
    }

    fun eventTitle(): String {
        return event.value?.title ?: ""
    }

    fun eventLocation(): String {
        return event.value?.location ?: ""
    }

    fun getEventCost(): String {
        if (event.value == null) {
            return ""
        }
        return if (event.value?.price == 0) {
            "Free"
        } else {
            "${getLocalCurrencySymbol()}${event.value?.price}"
        }
    }


    private fun getLocalCurrencySymbol(locale: Locale = Locale.getDefault()): String {
        return Currency.getInstance(locale).symbol
    }

    private fun parseCustomUtcString(): String {
        if (event.value?.time == null) {
            return ""
        }
        val parsedDate = ZonedDateTime.parse(event.value?.time) // Parse the UTC string to ZonedDateTime
        val formatter = DateTimeFormatter.ofPattern("MMM-dd, yyyy") // Define the desired format
        return parsedDate.format(formatter) // Format the parsed date
    }

    fun eventTime(): String {
        return parseCustomUtcString()
    }

    fun eventDescription(): String {
        return event.value?.longDescription?: ""
    }
}