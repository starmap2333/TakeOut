package com.example.take_out.viewmodels

import androidx.lifecycle.*
import com.example.take_out.data.Comment
import com.example.take_out.data.Sharing
import com.example.take_out.service.ApiResult
import com.example.take_out.service.Service
import com.example.take_out.service.SharingService

class CommentModel(val sharing: Sharing) : ViewModel() {
    private val refresh = MutableLiveData(true)
    private val _commentList: LiveData<ApiResult<List<Comment>>> = Transformations.switchMap(refresh) {
        Service.sharingService.getCommentBySharingId(sharing.id)
    }
    val loading = MutableLiveData<Boolean>()
    val commentList: LiveData<List<Comment>>
        get() = Transformations.map(_commentList) {
            loading.value = false
            it.data ?: listOf()
        }

    fun refreshData() {
        refresh.value = true
        loading.value = true
    }


    fun publish(content: String, userId: Int): LiveData<ApiResult<Boolean>> {
        val commentPublishParam = SharingService.CommentPublishParam(
                sharing.id, userId, content)
        return Service.sharingService.publishComment(commentPublishParam)
    }


    class ViewModelFactory(private val sharing: Sharing) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CommentModel(sharing) as T
        }
    }
}