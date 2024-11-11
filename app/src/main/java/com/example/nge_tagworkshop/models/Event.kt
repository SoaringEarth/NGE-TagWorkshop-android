package com.example.nge_tagworkshop.models

data class Event (
    var id: Int,
    var title: String,
    var time: String,
    var location: String,
    var description: String,
    var longDescription: String,
    var image: String,
    var price: Int,
    var category: Category
)

fun emptyEvent(): Event {
    return Event(
        id = 0, // Or a suitable default value for your ID
        title = "",
        time = "",
        location = "",
        description = "",
        longDescription = "",
        image = "",
        price = 0, // Or a suitable default value for price
        category = Category.Technology // Or a suitable default category
    )}