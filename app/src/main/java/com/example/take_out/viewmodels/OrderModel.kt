package com.example.take_out.viewmodels

import androidx.lifecycle.*
import com.example.take_out.data.MyOrder
import com.example.take_out.service.ApiResult
import com.example.take_out.service.Service

class OrderModel(private var userId: Int) : ViewModel() {
    private val refresh = MutableLiveData(true)
    private val _orderList: LiveData<ApiResult<List<MyOrder>>> = Transformations.switchMap(refresh) {
        Service.myOrderService.getMyOrderByUserId(userId)
    }
    val loading = MutableLiveData<Boolean>()
    val orderList: LiveData<List<MyOrder>>
        get() = Transformations.map(_orderList) {
            loading.value = false
            it.data ?: listOf()
        }

    fun setUserId(userId: Int) {
        this.userId = userId
        refreshData()
    }

    fun refreshData() {
        refresh.value = true
        loading.value = true
    }

    class ViewModelFactory(private val userId: Int) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OrderModel(userId) as T
        }
    }
}
