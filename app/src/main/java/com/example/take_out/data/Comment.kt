package com.example.take_out.data


data class Comment(
        var id: Int = -1,
        var user: User,
        var sharing: Sharing,
        var content: String
)