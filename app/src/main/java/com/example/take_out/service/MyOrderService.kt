package com.example.take_out.service

import androidx.lifecycle.LiveData
import com.example.take_out.data.MyOrder
import retrofit2.http.Body
import retrofit2.http.POST

interface MyOrderService {
    data class MyOrderParam(var userId: Int, var cuisineId: Int, var num: Int)

    @POST("order/")
    fun getMyOrderByUserId(id: Int): LiveData<ApiResult<List<MyOrder>>>

    @POST("order/gen")
    fun generateOrder(@Body myOrderParam: MyOrderParam): LiveData<ApiResult<MyOrder>>
}