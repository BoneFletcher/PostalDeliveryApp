package com.sdv.domain.usecase

import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User
import com.sdv.domain.repository.PostRepository

class GetPostUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(userId: Int,
        onSuccess: (List<Post>) -> Unit,
        onError: (com.sdv.domain.model.Error) -> Unit
    ) = repository.getPosts(userId, onSuccess, onError)
}