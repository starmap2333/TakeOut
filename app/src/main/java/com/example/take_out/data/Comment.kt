package com.example.take_out.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
        var id: Int = -1,
        var user: User,
        var sharing: Sharing,
        var content: String
) : Parcelable