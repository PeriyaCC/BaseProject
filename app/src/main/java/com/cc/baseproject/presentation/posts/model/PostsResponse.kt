package com.cc.baseproject.presentation.posts.model

import com.squareup.moshi.Json

data class PostsResponse(
	@Json(name="PostsResponse")
	val postsResponse: List<PostsResponseItem?>? = null
)

data class PostsResponseItem(
	@Json(name="id")
	val id: Int? = null,
	@Json(name="title")
	val title: String? = null,
	@Json(name="body")
	val body: String? = null,
	@Json(name="userId")
	val userId: Int? = null,
	@Json(name="comments")
	var comments: List<PostCommentsResponseItem>? = null,
)

data class PostCommentsResponseItem(
	@Json(name="name")
	val name: String? = null,
	@Json(name="postId")
	val postId: Int? = null,
	@Json(name="id")
	val id: Int? = null,
	@Json(name="body")
	val body: String? = null,
	@Json(name="email")
	val email: String? = null
)

