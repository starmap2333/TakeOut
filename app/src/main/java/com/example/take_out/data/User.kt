package com.example.take_out.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        var id: Int = -1,
        var phone: String = "",
        var name: String = "",
        var imageUUID: String? = null,
        var password: String = "",
        var address: String = ""
) : Parcelable