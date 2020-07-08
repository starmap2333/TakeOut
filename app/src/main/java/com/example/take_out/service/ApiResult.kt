package com.example.take_out.service

data class ApiResult<T>(
        var code: Int,
        var msg: String = "",
        var data: T? = null
)

enum class ResultCode(val code: Int) {
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);
}