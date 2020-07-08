package com.example.take_out.data


data class User(
        var id: Int = -1,
        var phone: String = "",
        var name: String = "",
        var imageUUID: String = "",
        var password: String = "",
        var address: String? = null
)