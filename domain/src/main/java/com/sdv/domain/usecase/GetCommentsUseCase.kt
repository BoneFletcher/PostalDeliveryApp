package com.sdv.domain.usecase

import com.sdv.domain.model.response.Comment
import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User
import com.sdv.domain.repository.PostRepository

class GetCommentsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(userId: Int,
        onSuccess: (List<Comment>) -> Unit,
        onError: (com.sdv.domain.model.Error) -> Unit
    ) = repository.getCommentsList(userId, onSuccess, onError)
}