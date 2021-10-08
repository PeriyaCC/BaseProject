package com.cc.baseproject.presentation.posts.domain

import com.cc.baseproject.data.model.ResultOf
import com.cc.baseproject.data.remote.PostService
import com.cc.baseproject.presentation.posts.model.PostCommentsResponseItem
import com.cc.baseproject.presentation.posts.model.PostsResponse
import com.cc.baseproject.presentation.posts.model.PostsResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsRepo(private val postsApi: PostService) : IPostsRepo {

    override fun getPostsWithCommentsUsingCall() {

        postsApi.getPostsUsingCall().enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.postsResponse?.forEach {
                        //get comments and add to post response
                        it?.comments = getCommentsByPostIt(it?.id)
                    }
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {

            }

        })
    }

    private fun getCommentsByPostIt(id: Int?): List<PostCommentsResponseItem>? {
        TODO("Not yet implemented")
    }

    override suspend fun getCommentsById(id: Int): List<PostCommentsResponseItem> {
        val comments = postsApi.getCommentsById(id)
        return comments!!
    }

    override suspend fun getPostWithComments() = withContext(Dispatchers.IO) {
        val postList = postsApi.getPosts()
        postList?.forEach {

            /*val postDetailDeferred = async { postsApi.getPostById(it?.id!!) }
            val postDetail = postDetailDeferred.await()*/

            val commentsDeferred = async { postsApi.getCommentsById(it?.id!!) }
            it?.comments = commentsDeferred.await()
        }
        postList
    }


    override suspend fun getPostWithCommentsUsingFlow(): Flow<List<PostsResponseItem?>?> = flow {
        val postList = postsApi.getPosts()
        withContext(Dispatchers.IO) {
            postList?.forEach {

                /*val postDetailDeferred = async { postsApi.getPostById(it?.id!!) }
                val postDetail = postDetailDeferred.await()*/

                val commentsDeferred = async { postsApi.getCommentsById(it?.id!!) }
                it?.comments = commentsDeferred.await()
            }
        }
        emit(postList)
    }

}