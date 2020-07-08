package com.example.take_out.service

import androidx.lifecycle.LiveData
import com.example.take_out.data.User
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface UserService {

    data class UserLoginParam(var phone: String, var password: String)

    data class UserAddressParam(var id: Int, var address: String)

    @POST("user/login")
    fun login(@Body userLoginParam: UserLoginParam): LiveData<ApiResult<Int>>

    @POST("user/enroll")
    fun enroll(@Body user: User): LiveData<ApiResult<Int>>

    fun updateAddress(@Body userAddressParam: UserAddressParam): LiveData<ApiResult<String>>

    @Multipart
    @POST("user/upload/face")
    fun uploadUserFace(@Part file: MultipartBody.Part, @Part("id") id: Int): LiveData<ApiResult<String>>
}