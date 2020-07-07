package com.example.take_out.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private const val ApiUrl = "http://localhost:8080/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(ApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    val userService: UserService by lazy {
        retrofit.create(userService::class.java)
    }
}
