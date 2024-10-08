package com.example.nge_tagworkshop.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

enum class Category(val value: String) {
    All("All"),
    Technology("Technology"),
    ArtDesign("Art & Design"),
    Music("Music"),
    FoodDrink("Food & Drink"),
    Fashion("Fashion"),
    Sports("Sports"),
}

data class Event (
    var id: Int,
    var title: String,
    var time: String,
    var location: String,
    var description: String,
    var image: String,
    var price: Int,
    var category: Category
)

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