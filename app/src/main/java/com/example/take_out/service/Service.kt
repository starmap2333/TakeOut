package com.example.take_out.service

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.take_out.R
import com.example.take_out.service.Service.ApiUrl
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    const val ApiUrl = "http://10.0.2.2:8080/"
    private val retrofit: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(ApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()

    val userService: UserService
        get() = retrofit.create(UserService::class.java)


    val storeService: StoreService
        get() = retrofit.create(StoreService::class.java)


    val sharingService: SharingService
        get() = retrofit.create(SharingService::class.java)
}

fun ByteArray.toMultiPartBodyPart(): MultipartBody.Part {
    val fileBody = RequestBody.create(MediaType.parse("file"), this)
    return MultipartBody.Part.createFormData("file", "temp", fileBody)
}

@JvmOverloads
fun ImageView.loadUrl(context: Context, imgUUID: String?,
                      placeholder: Int = R.drawable.logo,
                      fallback: Int = R.drawable.fallback) {
    Glide.with(context)
            .load(if ((imgUUID == null) or (imgUUID == "null")) null else "$ApiUrl/img/$imgUUID"
            )
            .optionalCenterCrop()
            .placeholder(placeholder)
            .fallback(fallback)
            .into(this)
}
