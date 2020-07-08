package com.example.take_out.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.take_out.data.Sharing
import com.example.take_out.service.ApiResult
import com.example.take_out.service.Service

class SharingModel : ViewModel() {

    private val refresh = MutableLiveData(true)
    private val _sharingList: LiveData<ApiResult<List<Sharing>>> = Transformations.switchMap(refresh) {
        Service.sharingService.getAllSharing()
    }
    val loading = MutableLiveData<Boolean>()
    val sharingList: LiveData<List<Sharing>>
        get() = Transformations.map(_sharingList) {
            loading.value = false
            it.data ?: listOf()
        }

    fun refreshData() {
        refresh.value = true
        loading.value = true
    }
}
