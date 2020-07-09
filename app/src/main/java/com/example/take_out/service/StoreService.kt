package com.example.take_out.service

import androidx.lifecycle.MutableLiveData
import com.example.take_out.data.Cuisine
import com.example.take_out.data.Store
import okhttp3.MultipartBody
import retrofit2.http.*

interface StoreService {
    data class CuisineUploadParam(var storeId: Int, var weight: Int,
                                  var taste: String, var sale: Int,
                                  var price: Double, var name: String)

    @POST("store/all")
    fun getAllStore(): MutableLiveData<ApiResult<List<Store>>>

    @POST("store/cuisine/all")
    fun getAllCuisineByStoreId(@Query("id") id: Int): MutableLiveData<ApiResult<List<Cuisine>>>

    @POST("store/cuisine/page")
    fun getPagedCuisineByStoreId(id: Int, page: Int, size: Int): MutableLiveData<ApiResult<List<Cuisine>>>

    @POST("store/id")
    fun getStoreById(id: Int): MutableLiveData<ApiResult<Store?>>

    @GET("store/page")
    fun getPagedStore(page: Int, size: Int): MutableLiveData<ApiResult<List<Store>>>

    @Multipart
    @POST("store/cuisine/upload")
    fun uploadCuisine(@Part file: MultipartBody.Part,
                      @Part("cuisineUploadParam") cuisineUploadParam: CuisineUploadParam)
            : MutableLiveData<ApiResult<Boolean>>
}