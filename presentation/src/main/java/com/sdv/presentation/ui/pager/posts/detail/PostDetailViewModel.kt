package com.sdv.presentation.ui.pager.posts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User
import com.sdv.domain.usecase.GetPostUseCase
import com.sdv.presentation.base.BaseViewModel

class PostDetailViewModel(private val getPostUseCase: GetPostUseCase) : BaseViewModel() {
    private val _postList: MutableLiveData<List<Post>> = MutableLiveData()
    val postList: LiveData<List<Post>> = _postList
    var postDetail: MutableLiveData<Post> = MutableLiveData()
    init {
        getPostList()
    }

    fun setPostDetail(post: Post){
        postDetail.postValue(post)
    }

    private fun getPostList() {
        launchAsync {
            getPostUseCase.invoke(1, {
                //setPage(it as ArrayList<Post>)
            }, {
               // showError.postValue(true)
            })
        }
    }

}