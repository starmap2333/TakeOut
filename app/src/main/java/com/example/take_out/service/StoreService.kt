package com.example.take_out.service

import androidx.lifecycle.LiveData
import com.example.take_out.data.Cuisine
import com.example.take_out.data.Store
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StoreService {
    data class CuisineUploadParam(var storeId: Int, var weight: Int,
                                  var taste: String, var sale: Int,
                                  var price: Double, var name: String)

    @POST("store/cuisine/all")
    fun getAllCuisinsByStoreId(id: Int): LiveData<ApiResult<Set<Cuisine>>>

    @POST("store/cuisine/page")
    fun getPagedCuisineByStoreId(id: Int, page: Int, size: Int): LiveData<ApiResult<List<Cuisine>>>

    @POST("store/id")
    fun getStoreById(id: Int): LiveData<ApiResult<Store?>>

    @GET("store/page")
    fun getPagedStore(page: Int, size: Int): LiveData<ApiResult<List<Store>>>

    @Multipart
    @POST("store/cuisine/upload")
    fun uploadCuisine(@Part file: MultipartBody.Part,
                      @Part("cuisineUploadParam") cuisineUploadParam: CuisineUploadParam)
            : LiveData<ApiResult<Boolean>>
}