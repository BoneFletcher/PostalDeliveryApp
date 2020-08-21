package com.sdv.presentation.ui.pager.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdv.domain.model.response.Comment
import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User
import com.sdv.domain.usecase.GetCommentsUseCase
import com.sdv.domain.usecase.GetPostUseCase
import com.sdv.domain.usecase.GetUserListUseCase
import com.sdv.presentation.base.BaseViewModel

class UsersViewModel(private val getUserListUseCase: GetUserListUseCase,
                     private val getPostUseCase: GetPostUseCase,
                     private val getCommentsUseCase: GetCommentsUseCase) : BaseViewModel() {
    private var origList: ArrayList<User> = ArrayList()
    private var chunkedList: List<List<User>>  = ArrayList()
    private var currentPage: Int=0
    private val _userList: MutableLiveData<List<User>> = MutableLiveData()
    val userList: LiveData<List<User>> = _userList
    val showError: MutableLiveData<String> = MutableLiveData()
    val disablePrevious: MutableLiveData<Boolean> = MutableLiveData()
    val disableNext: MutableLiveData<Boolean> = MutableLiveData()
    val listProgression: MutableLiveData<Triple<Int,Int,Int>> = MutableLiveData()

    private val _postList: MutableLiveData<List<Post>> = MutableLiveData()
    val postList: LiveData<List<Post>> = _postList
    private var origListPost: ArrayList<Post> = ArrayList()
    private var chunkedListPost: List<List<Post>>  = ArrayList()
    private var currentPagePost: Int=0
    val disablePreviousPost: MutableLiveData<Boolean> = MutableLiveData()
    val disableNextPost: MutableLiveData<Boolean> = MutableLiveData()
    val listProgressionPost: MutableLiveData<Triple<Int,Int,Int>> = MutableLiveData()
    val changePage: MutableLiveData<Int> = MutableLiveData()

    val showProgress: MutableLiveData<Boolean> = MutableLiveData()

    private val _commentList: MutableLiveData<Pair<String,List<Comment>>> = MutableLiveData()
    val commentList: LiveData<Pair<String,List<Comment>>> = _commentList
    var postDetail: MutableLiveData<Post> = MutableLiveData()

    private lateinit var currentUser:User

    init {
        getUserList()
    }

    private fun getUserList() {
        launchAsync {
            getUserListUseCase.invoke({
                setPage(it as ArrayList<User>)
            },{
                showError.postValue(it.message)
            })
        }
    }

    private fun getPostList(userId: Int) {
        launchAsync {
            getPostUseCase.invoke(userId, {
                showProgress.postValue(false)
                setPagePost(it as ArrayList<Post>)
            }, {
                showProgress.postValue(false)
                showError.postValue(it.message)
            })
        }
    }

    fun getComments(postId: Int){
        launchAsync {
            getCommentsUseCase.invoke(postId, {
                _commentList.postValue(Pair(currentUser.name,it))
            }, {
                showError.postValue(it.message)
            })
        }
    }

    fun setPostDetail(post: Post){
        postDetail.postValue(post)
    }

    private fun setPage(list: ArrayList<User>){
        origList = list
        chunkedList = origList.chunked(6)
        _userList.postValue(chunkedList[currentPage])
        setChunkedList()
    }

    private fun setPagePost(list: ArrayList<Post>){
        origListPost = list
        chunkedListPost = origListPost.chunked(6)
        _postList.postValue(chunkedListPost[currentPagePost])
        setChunkedPostList()
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

    fun getNextPost(){
        currentPagePost++
        setChunkedPostList()
        clearImagePost()
    }

    fun getPreviousPost(){
        currentPagePost--
        setChunkedPostList()
        clearImagePost()
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

    private fun clearImagePost(){
        when (currentPagePost) {
            chunkedListPost.size-1 -> {
                disableNextPost.postValue(true)
                disablePreviousPost.postValue(false)
            }
            0 -> {
                disablePreviousPost.postValue(true)
                disableNextPost.postValue(false)
            }
            else -> {
                disablePreviousPost.postValue(false)
                disableNextPost.postValue(false)
            }
        }
    }

    private fun setChunkedList() {
        val chunk = chunkedList[currentPage]
        _userList.postValue(chunk)
        listProgression.postValue(
            Triple(
                origList.indexOf(chunk[0]),
                origList.indexOf(chunk[chunk.size-1]),
                origList.size
            )
        )
    }

    private fun setChunkedPostList() {
        val chunk = chunkedListPost[currentPagePost]
        _postList.postValue(chunk)
        listProgressionPost.postValue(
            Triple(
                origListPost.indexOf(chunk[0]),
                origListPost.indexOf(chunk[chunk.size-1]),
                origListPost.size
            )
        )
    }

    fun openPosts(user: User){
        currentUser=user
        showProgress.postValue(true)
        getPostList(user.id)
        changePage.postValue(1)
    }
}