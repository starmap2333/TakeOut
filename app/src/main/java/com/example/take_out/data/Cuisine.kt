package com.example.take_out.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cuisine(
        var id: Int = -1,
        var weight: Int = 0,
        var taste: String = "",
        var sale: Int = 0,
        var price: Double = 0.0,
        var name: String = "",
        var imageUUID: String? = null,
        var store: Store
) : Parcelable