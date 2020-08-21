package com.sdv.domain.repository

import com.sdv.domain.model.Error
import com.sdv.domain.model.response.Comment
import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User


interface PostRepository {
    suspend fun getPosts(userId: Int, onSuccess: (List<Post>) -> Unit, onError: (Error) -> Unit)
    suspend fun getUsersList(onSuccess: (List<User>) -> Unit, onError: (Error) -> Unit)
    suspend fun getCommentsList(postId: Int, onSuccess: (List<Comment>) -> Unit, onError: (Error) -> Unit)
}