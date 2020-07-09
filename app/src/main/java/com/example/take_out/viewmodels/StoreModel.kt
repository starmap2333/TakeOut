package com.example.take_out.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.take_out.data.Store
import com.example.take_out.service.ApiResult
import com.example.take_out.service.Service

class StoreModel : ViewModel() {

    private val refresh = MutableLiveData(true)
    private val _storeList: LiveData<ApiResult<List<Store>>> = Transformations.switchMap(refresh) {
        Service.storeService.getAllStore()
    }
    val loading = MutableLiveData<Boolean>()
    val storeList: LiveData<List<Store>>
        get() = Transformations.map(_storeList) {
            loading.value = false
            it.data ?: listOf()
        }

    fun refreshData() {
        refresh.value = true
        loading.value = true
    }
}
