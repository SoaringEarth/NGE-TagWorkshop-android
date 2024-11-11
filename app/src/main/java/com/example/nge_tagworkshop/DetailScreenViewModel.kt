package com.example.nge_tagworkshop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nge_tagworkshop.api.EventsRepository
import com.example.nge_tagworkshop.api.PexelRepository
import com.example.nge_tagworkshop.api.Photo
import com.example.nge_tagworkshop.api.WeatherData
import com.example.nge_tagworkshop.api.WeatherRepository
import com.example.nge_tagworkshop.models.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DetailScreenViewModel(
    var eventsRepository: EventsRepository = EventsRepository(),
    var weatherRepository: WeatherRepository = WeatherRepository(),
    private var photoRepository: PexelRepository = PexelRepository(),
    var eventId: Int
): ViewModel() {

    var weather: List<WeatherData> by mutableStateOf(listOf())

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    private val _eventPhoto = MutableStateFlow<Photo?>(null)
    val eventPhoto: StateFlow<Photo?> = _eventPhoto

    suspend fun fetchEventAndImage(eventID: String) {
        viewModelScope.launch {
            weather = weatherRepository.getWeather()

            val fetchedEvent = eventsRepository.getEvent(eventId)
            _event.value = fetchedEvent

            fetchedEvent?.title?.let { title ->
                _eventPhoto.value = photoRepository.getPhoto(query = title)
            }
        }
    }

    fun eventTitle(): String {
        return event.value?.title ?: ""
    }

    fun eventLocation(): String {
        return event.value?.location ?: ""
    }

//    fun eventWeather(): WeatherData? {
//        return weather.find { location.contains(it.city) }
//    }

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