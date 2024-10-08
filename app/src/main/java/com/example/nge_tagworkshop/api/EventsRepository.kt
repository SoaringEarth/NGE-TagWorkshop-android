package com.example.nge_tagworkshop.api

import com.example.nge_tagworkshop.models.Event
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class EventsRepository {
    private var service: EventsService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/SoaringEarth/EventsAPI/main/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(EventsService::class.java)
    }

    suspend fun getEvents(count: Int): List<Event> {
        val events = service.listEvents()
        return events.shuffled().take(count)
    }
}

interface EventsService {
    @GET("mockEvents.json")
    suspend fun listEvents(): List<Event>
}