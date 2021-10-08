package com.cc.baseproject.presentation.posts.domain

import com.cc.baseproject.presentation.posts.model.*
import kotlinx.coroutines.flow.Flow

interface IPostsRepo {

    fun getPostsWithCommentsUsingCall()
    suspend fun getCommentsById(id : Int) : List<PostCommentsResponseItem>
    suspend fun getPostWithComments() : List<PostsResponseItem?>?
    suspend fun getPostWithCommentsUsingFlow() : Flow<List<PostsResponseItem?>?>

}