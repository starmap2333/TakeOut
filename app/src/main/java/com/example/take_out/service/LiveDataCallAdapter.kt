package com.example.take_out.service

import androidx.lifecycle.MutableLiveData
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

const val LIVE_DATA_CALL_ADAPTER_ERR_MSG = "retrofit call failed!"

class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, MutableLiveData<T>> {
    @Suppress("UNCHECKED_CAST")
    override fun adapt(call: Call<T>): MutableLiveData<T> {
        return object : MutableLiveData<T>() {
            private val started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            postValue(ApiResult(code = ResultCode.FAIL.code,
                                    msg = LIVE_DATA_CALL_ADAPTER_ERR_MSG,
                                    data = null) as T)
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(response.body())
                        }
                    })
                }
            }
        }
    }

    override fun responseType() = responseType
}

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != MutableLiveData::class.java) throw IllegalStateException("return type must be LiveData")
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        return LiveDataCallAdapter<Any>(observableType)
    }

}