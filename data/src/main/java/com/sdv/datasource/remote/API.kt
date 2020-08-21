package com.sdv.datasource.remote

import com.sdv.net.PostAPI
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.sdv.domain.model.Result
import com.sdv.domain.model.Error
import com.sdv.domain.model.response.Comment
import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User

class API(private val postAPI: PostAPI) {

    private suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        OnSuccess: (T) -> Unit,
        OnError: (Error) -> Unit
    ) {
        when (val result: Result<T> = safeApiResult(call)) {
            is Result.Success ->
                OnSuccess(result.data)
            is Result.Error -> {
                OnError(result.exception)
            }
        }
    }

    private suspend fun <T : Any> safeApiCallWithPagination(
        call: suspend () -> Response<T>,
        OnSuccess: (T) -> Unit,
        OnError: (Error) -> Unit
    ): T? {

        val result: Result<T> = safeApiResult(call)
        var data: T? = null

        when (result) {
            is Result.Success ->
                return result.data
            is Result.Error -> {
                OnError(result.exception)
            }
        }
        return null
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Result<T> {
        var response: Response<T>
        try {
            response = call.invoke()
        } catch (e: Exception) {
            return if (e is UnknownHostException || e is SocketTimeoutException)
                Result.Error(Error(
                        "No internet connection",
                        666
                    )
                )
            else
                Result.Error(
                    Error(e.message ?: "", -1))
        }
        if (response.isSuccessful) return Result.Success(response.body()!!)
        return Result.Error(
            Error(if (response.code() == 500) "Oops! Something went wrong" else response.errorBody()?.string() ?: "", response.code()
            )
        )
    }

    suspend fun getUsersList(
        onSuccess: (List<User>) -> Unit,
        onError: (Error) -> Unit

    ) {
        safeApiCall(
            call = {
                postAPI.getUsersList()
            }
            , OnSuccess = {
                onSuccess(it)
            }
            , OnError = {
                onError(it)
            })
    }

    suspend fun getPostsList(
        userId: Int,
        onSuccess: (List<Post>) -> Unit,
        onError: (Error) -> Unit

    ) {
        safeApiCall(
            call = {
                postAPI.getPostsList(userId)
            }
            , OnSuccess = {
                onSuccess(it)
            }
            , OnError = {
                onError(it)
            })
    }

    suspend fun getCommentList(
        postId: Int,
        onSuccess: (List<Comment>) -> Unit,
        onError: (Error) -> Unit

    ) {
        safeApiCall(
            call = {
                postAPI.getCommentsList(postId)
            }
            , OnSuccess = {
                onSuccess(it)
            }
            , OnError = {
                onError(it)
            })
    }
}