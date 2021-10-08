package com.cc.baseproject.presentation.posts.viewmodel

import androidx.lifecycle.*
import com.cc.baseproject.data.model.ResultOf
import com.cc.baseproject.presentation.posts.domain.IPostsRepo
import com.cc.baseproject.presentation.posts.model.PostCommentsResponseItem
import com.cc.baseproject.presentation.posts.model.PostsResponseItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class PostsVM(val postsRepo: IPostsRepo) : ViewModel(), CoroutineScope {

    private val parentJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    val obPostsWithComment = MutableLiveData<ResultOf<List<PostsResponseItem?>?>>()
    val obComments = MutableLiveData<ResultOf<List<PostCommentsResponseItem>>>()

    fun performDelayAction() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(100)
                // do action
            }
        }
    }

    suspend fun performDelayAction2() = withContext(Dispatchers.IO) {
        delay(100)
        // do action
    }

    fun getComments(postId : Int){
        launch {
            runCatching {
                postsRepo.getCommentsById(postId)
            }.onSuccess {
                if (it.isEmpty())
                    obComments.value = ResultOf.Empty("No Posts Available!")
                else
                    obComments.value = ResultOf.Success(it)
            }.onFailure {
                obComments.value = ResultOf.Failure(it.localizedMessage)
            }
        }
    }

    fun getPostsWithComments() {
        launch {
            runCatching {
                postsRepo.getPostWithComments()
            }.onSuccess {
                if (it?.isEmpty() == true)
                    obPostsWithComment.value = ResultOf.Empty("No Posts Available!")
                else
                    obPostsWithComment.value = ResultOf.Success(it)
            }.onFailure {
                obPostsWithComment.value = ResultOf.Failure(it.localizedMessage)
            }
        }
    }

    fun getPostsWithCommentsUsingFlow() = liveData {
        emit(ResultOf.Progress(true))
        postsRepo.getPostWithCommentsUsingFlow()
            .catch { emit(ResultOf.Failure(it.message, it)) }
            .collect {
                emit(if(it?.isEmpty() == true) ResultOf.Empty("No posts available") else ResultOf.Success(it))
            }
        emit(ResultOf.Progress(false))
    }


}