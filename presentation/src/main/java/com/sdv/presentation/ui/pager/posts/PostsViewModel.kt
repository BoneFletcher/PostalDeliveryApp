package com.sdv.presentation.ui.pager.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User
import com.sdv.domain.usecase.GetPostUseCase
import com.sdv.presentation.base.BaseViewModel

class PostsViewModel(private val getPostUseCase: GetPostUseCase) : BaseViewModel() {
    private val _postList: MutableLiveData<List<Post>> = MutableLiveData()
    val postList: LiveData<List<Post>> = _postList
    private var origList: ArrayList<Post> = ArrayList()
    private var chunkedList: List<List<Post>>  = ArrayList()
    private var currentPage: Int=0
    val showError: MutableLiveData<Boolean> = MutableLiveData()
    val disablePrevious: MutableLiveData<Boolean> = MutableLiveData()
    val disableNext: MutableLiveData<Boolean> = MutableLiveData()
    val listProgression: MutableLiveData<Triple<Int,Int,Int>> = MutableLiveData()
    init {
        getPostList()
    }

    private fun getPostList() {
        launchAsync {
            getPostUseCase.invoke(1, {
                setPage(it as ArrayList<Post>)
            }, {
                showError.postValue(true)
            })
        }
    }

    private fun setPage(list: ArrayList<Post>){
        origList = list
        chunkedList = origList.chunked(6)
        _postList.postValue(chunkedList[currentPage])
        setChunkedList()
    }

    fun getNext(){
        currentPage++
        setChunkedList()
        clearImage()
    }

    fun getPrevious(){
        currentPage--
        setChunkedList()
        clearImage()
    }

    private fun clearImage(){
        when (currentPage) {
            chunkedList.size-1 -> {
                disableNext.postValue(true)
                disablePrevious.postValue(false)
            }
            0 -> {
                disablePrevious.postValue(true)
                disableNext.postValue(false)
            }
            else -> {
                disablePrevious.postValue(false)
                disableNext.postValue(false)
            }
        }
    }

    private fun setChunkedList() {
        val chunk = chunkedList[currentPage]
        _postList.postValue(chunk)
        listProgression.postValue(
            Triple(
                origList.indexOf(chunk[0]),
                origList.indexOf(chunk[chunk.size-1]),
                origList.size
            )
        )
    }
}