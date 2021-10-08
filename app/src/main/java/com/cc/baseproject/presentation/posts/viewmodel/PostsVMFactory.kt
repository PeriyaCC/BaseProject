package com.cc.baseproject.presentation.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cc.baseproject.presentation.posts.domain.IPostsRepo
import com.cc.baseproject.network.NetworkModule
import com.cc.baseproject.presentation.posts.domain.PostsRepo

class PostsVMFactory : ViewModelProvider.Factory{

    init {
        getInstance()
    }

    companion object {
        @Volatile
        private var INSTANCE: IPostsRepo? = null

        fun getInstance() =
            INSTANCE ?: synchronized(PostsVMFactory::class.java) {
                INSTANCE ?: PostsRepo(
                    NetworkModule.makeApiService()
                ).also { INSTANCE = it }
            }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IPostsRepo::class.java).newInstance(INSTANCE)
    }

}