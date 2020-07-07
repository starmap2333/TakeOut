package com.example.take_out.data

data class Sharing(
        val id: Int = -1,
        val user: User,
        var title: String = "",
        var content: String = "",
        var imageUUID: String? = null
)