package com.example.take_out.data


data class Cuisine(
        var id: Int = -1,
        var weight: Int = 0,
        var taste: String = "",
        var sale: Int = 0,
        var price: Double = 0.0,
        var name: String = "",
        var imageUUID: String = "",
        var store: Store
)