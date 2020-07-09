package com.example.take_out.viewmodels

import androidx.lifecycle.*
import com.example.take_out.data.Cuisine
import com.example.take_out.data.Store
import com.example.take_out.service.ApiResult
import com.example.take_out.service.Service

class CuisineModel(store: Store) : ViewModel() {
    private val refresh = MutableLiveData(true)
    private val _cuisineList: LiveData<ApiResult<List<Cuisine>>> = Transformations.switchMap(refresh) {
        Service.storeService.getAllCuisineByStoreId(store.id)
    }
    val loading = MutableLiveData<Boolean>()
    val cuisineList: LiveData<List<Cuisine>>
        get() = Transformations.map(_cuisineList) {
            loading.value = false
            it.data ?: listOf()
        }

    fun refreshData() {
        refresh.value = true
        loading.value = true
    }

    class ViewModelFactory(private val store: Store) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CuisineModel(store) as T
        }
    }
}