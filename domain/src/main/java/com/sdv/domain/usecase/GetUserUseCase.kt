package com.sdv.domain.usecase

import com.sdv.domain.model.response.User
import com.sdv.domain.repository.PostRepository

class GetUserListUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(
        onSuccess: (List<User>) -> Unit,
        onError: (com.sdv.domain.model.Error) -> Unit
    ) = repository.getUsersList(onSuccess, onError)
}