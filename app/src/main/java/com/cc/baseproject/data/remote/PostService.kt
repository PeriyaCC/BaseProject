package com.cc.baseproject.data.remote

import com.cc.baseproject.presentation.posts.model.PostCommentsResponseItem
import com.cc.baseproject.presentation.posts.model.PostsResponse
import com.cc.baseproject.presentation.posts.model.PostsResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {

    @GET("posts")
    fun getPostsUsingCall(): Call<PostsResponse>

    @GET("posts")
    suspend fun getPosts(): List<PostsResponseItem?>?

    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: Int): PostsResponseItem

    @GET("comments")
    suspend fun getCommentsById( @Query("postId") postId: Int): List<PostCommentsResponseItem>?

}