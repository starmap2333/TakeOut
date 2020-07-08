package com.example.take_out.service

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.take_out.R
import com.example.take_out.service.Service.ApiUrl
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

    val myOrderService: MyOrderService
        get() = retrofit.create(MyOrderService::class.java)
}

@JvmOverloads
fun ImageView.loadUrl(context: Context, imgUUID: String?,
                      placeholder: Int = R.drawable.recycler_corner,
                      fallback: Int = R.drawable.recycler_totalview) {
    Glide.with(context)
            .load(imgUUID?.let {
                if (imgUUID != "null") {
                    "$ApiUrl/$imgUUID"
                }
            })
            .placeholder(placeholder)
            .fallback(fallback)
            .into(this)
}
