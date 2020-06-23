package com.maxproj.purebbs.config

import androidx.lifecycle.ViewModel
import com.maxproj.purebbs.post.Post

class SharedViewModel: ViewModel() { // shared viewModel in activity
    var post: Post? = null
}