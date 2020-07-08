package com.example.take_out

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.take_out.data.User

class TakeOutApplication : Application() {
    val userLiveData = MutableLiveData(User())
    val user get() = userLiveData.value!!
}