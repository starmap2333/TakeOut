package com.example.take_out.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.take_out.data.Comment
import com.example.take_out.data.Sharing
import okhttp3.MultipartBody
import retrofit2.http.*

interface SharingService {
    data class SharingPublishParam(var title: String, var content: String, var userId: Int)
    data class CommentPublishParam(var sharingId: Int, var userId: Int, var content: String)

    @GET("sharing/all")
    fun getAllSharing(): MutableLiveData<ApiResult<List<Sharing>>>

    @GET("sharing/page")
    fun getPagedSharing(page: Int, size: Int): MutableLiveData<ApiResult<List<Sharing>>>

    @POST("sharing/comment")
    fun getCommentBySharingId(@Query("id") id: Int): MutableLiveData<ApiResult<List<Comment>>>

    @POST("sharing/comment/page")
    fun getPagedCommentBySharingId(sharingId: Int, page: Int, size: Int): MutableLiveData<ApiResult<List<Comment>>>

    @POST("sharing/comment/publish")
    fun publishComment(@Body commentPublishParam: CommentPublishParam): MutableLiveData<ApiResult<Boolean>>

    @Multipart
    @POST("sharing/publish")
    fun publishSharing(
            @Part file: MultipartBody.Part,
            @Part("title") title: String,
            @Part("content") content: String,
            @Part("userId") userId: Int
    ): MutableLiveData<ApiResult<Sharing>>

    @DELETE("sharing/delete")
    fun deleteSharing(userId: Int, sharingId: Int): LiveData<ApiResult<Boolean>>

}