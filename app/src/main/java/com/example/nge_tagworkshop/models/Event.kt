package com.example.nge_tagworkshop.models

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