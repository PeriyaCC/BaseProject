package com.cc.baseproject.presentation.posts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.cc.baseproject.presentation.posts.viewmodel.PostsVM
import com.cc.baseproject.R
import com.cc.baseproject.data.model.ResultOf
import com.cc.baseproject.presentation.posts.viewmodel.PostsVMFactory
import com.cc.baseproject.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsActivity : AppCompatActivity() {

    private val postsVM by viewModels<PostsVM> { PostsVMFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeToObservers()
        doFetchApi()
    }

    private fun subscribeToObservers() {

        postsVM.obComments.observe(this,{
            when(it){
                is ResultOf.Progress -> showToast(if(it.loading) "Loading.. " else "Loaded")
                is ResultOf.Success -> {
                    Log.d("Posts","Comments ${it.value.size}")
                }
                is ResultOf.Empty -> showToast("No comments available!")
                is ResultOf.Failure -> {
                    Log.e("Posts", it.message.toString())
                }
            }
        })

        postsVM.obPostsWithComment.observe(this, {
            when (it) {
                is ResultOf.Progress -> showToast(if(it.loading) "Loading.. " else "Loaded")
                is ResultOf.Success -> {
                    it.value?.forEach { item ->
                        Log.d("Posts","Comments ${item?.comments?.size}")
                    }
                }
                is ResultOf.Empty -> showToast("No posts available!")
                is ResultOf.Failure -> {
                    Log.e("Posts", it.message.toString())
                    showToast(it.message!!)
                }
            }
        })

        // using flow
        postsVM.getPostsWithCommentsUsingFlow().observe(this, {
            when (it) {
                is ResultOf.Progress -> showToast(if(it.loading) "Loading.. " else "Loaded")
                is ResultOf.Success -> {
                    it.value?.forEach { item ->
                        Log.d("Posts","Comments ${item?.comments?.size}")
                    }
                }
                is ResultOf.Empty -> showToast("No posts available!")
                is ResultOf.Failure -> {
                    Log.e("Posts", it.message.toString())
                    showToast(it.message!!)
                }
            }
        })
    }

    private fun doFetchApi() {
        //postsVM.getPostsWithComments()
    }

    fun performDelayAction() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                delay(100)
                // do action
            }
        }
    }

    fun performDelayAction2() {
        //postsVM.performDelayAction2()
        postsVM.performDelayAction()
    }

    fun asyncAwait() {

    }

}