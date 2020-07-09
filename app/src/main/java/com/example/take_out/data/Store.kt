package com.example.take_out.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Store(
        var id: Int = -1,
        var phone: String = "",
        var name: String = "",
        var address: String = "",
        var description: String = "",
        var imageUUID: String? = null
) : Parcelable