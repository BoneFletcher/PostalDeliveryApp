package com.sdv.net

import com.sdv.domain.model.response.Comment
import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PostAPI {

    @GET("users")
    suspend fun getUsersList(): Response<List<User>>

    @GET("posts")
    suspend fun getPostsList(@Query("userId") userId: Int?): Response<List<Post>>

    @GET("comments")
    suspend fun getCommentsList(@Query("postId") postId: Int?): Response<List<Comment>>

}