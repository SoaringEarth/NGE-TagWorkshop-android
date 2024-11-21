package com.example.nge_tagworkshop

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nge_tagworkshop.api.EventsRepository
import com.example.nge_tagworkshop.api.Weather
import com.example.nge_tagworkshop.api.WeatherRepository
import com.example.nge_tagworkshop.models.Category
import com.example.nge_tagworkshop.models.Event
import kotlinx.coroutines.launch

class ListViewModel(repository: EventsRepository = EventsRepository(), weatherRepository: WeatherRepository = WeatherRepository()): ViewModel() {
    var events: List<Event> by mutableStateOf(listOf())
    var weather: List<Weather> by mutableStateOf(listOf())
    var reposList: List<String> by mutableStateOf(listOf())
    var popularCategories: List<Category> by mutableStateOf(listOf())
    var selectedCategory: MutableState<Category> = mutableStateOf(Category.All)

    init {
        viewModelScope.launch {
            events = repository.getEvents(10)
            weather = weatherRepository.getWeather()
            popularCategories = getCategories(events)
        }
    }

    fun getEvents(category: Category, reverseOrder: Boolean = false): List<Event> {
        if (category == Category.All) {
            return if (reverseOrder) {
                events.reversed()
            } else {
                events
            }
        }
        return events.filter { event -> event.category == category }
    }

    fun getWeather(location: String): Weather? {
        return weather.find { location.contains(it.city) }
    }

    private fun getCategories(events: List<Event>): List<Category> {
        if (events.isEmpty()) {
            return emptyList()
        }
        return listOf(Category.All).plus(events.mapNotNull { event ->
            Category.entries.find { it == event.category }
        }.distinct())
    }
}