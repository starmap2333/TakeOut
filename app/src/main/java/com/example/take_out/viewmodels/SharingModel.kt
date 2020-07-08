package com.example.take_out.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.take_out.data.Sharing
import com.example.take_out.service.ApiResult
import com.example.take_out.service.ResultCode
import com.example.take_out.service.Service

class SharingModel : ViewModel() {
    private val _sharingList: MutableLiveData<ApiResult<List<Sharing>>> by lazy {
        Service.sharingService.getAllSharing()
    }


    val sharingList: LiveData<List<Sharing>>
        get() = Transformations.map(_sharingList) {
            it.data ?: listOf()
        }

    val ready: LiveData<Boolean>
        get() = Transformations.map(_sharingList) {
            it.code == ResultCode.SUCCESS.code
        }
}
