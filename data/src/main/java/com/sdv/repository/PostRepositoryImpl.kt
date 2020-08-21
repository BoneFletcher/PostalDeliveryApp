package com.sdv.repository

import com.sdv.datasource.remote.API
import com.sdv.domain.model.response.User
import com.sdv.domain.repository.PostRepository
import com.sdv.domain.model.Error
import com.sdv.domain.model.response.Comment
import com.sdv.domain.model.response.Post

class PostRepositoryImpl(private val api: API): PostRepository {
    override suspend fun getPosts(userId: Int,
                                  onSuccess: (List<Post>) -> Unit,
                                  onError: (Error) -> Unit
    ) = api.getPostsList(userId, onSuccess, onError)

    override suspend fun getCommentsList(postId: Int,
                                  onSuccess: (List<Comment>) -> Unit,
                                  onError: (Error) -> Unit
    ) = api.getCommentList(postId, onSuccess, onError)

    override suspend fun getUsersList(onSuccess: (List<User>) -> Unit,
                                      onError: (Error) -> Unit
    ) = api.getUsersList(onSuccess, onError)
}