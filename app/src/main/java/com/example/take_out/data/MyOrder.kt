package com.example.take_out.data

import java.util.*


data class MyOrder(
        var id: Int = -1,
        var user: User,
        var cuisine: Cuisine,
        var price: Double,
        var num: Int = 1,
        var state: Boolean = false,
        val date: Date = Date()
)