package com.example.take_out.service

data class ApiResult<T>(
        var code: Int,
        var msg: String = "",
        var data: T? = null
)