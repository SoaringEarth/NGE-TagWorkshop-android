package com.example.nge_tagworkshop

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nge_tagworkshop.api.EventsRepository
import com.example.nge_tagworkshop.models.Category
import com.example.nge_tagworkshop.models.Event
import kotlinx.coroutines.launch

class ListViewModel(repository: EventsRepository = EventsRepository()): ViewModel() {
    var events: List<Event> by mutableStateOf(listOf())
    var reposList: List<String> by mutableStateOf(listOf())
    var popularCategories: List<Category> by mutableStateOf(listOf())
    var selectedCategory: MutableState<Category> = mutableStateOf(Category.All)

    init {
        viewModelScope.launch {
            events = repository.getEvents(10)
            popularCategories = getCategories(events)
        }
    }

    fun getEvents(category: Category): List<Event> {
        if (category == Category.All) return events
        return events.filter { event -> event.category == category }
    }

    private fun getCategories(events: List<Event>): List<Category> {
        if (events.isEmpty()) return emptyList()
        return listOf(Category.All).plus(events.mapNotNull { event ->
            Category.entries.find { it == event.category }
        }.distinct())
    }
}