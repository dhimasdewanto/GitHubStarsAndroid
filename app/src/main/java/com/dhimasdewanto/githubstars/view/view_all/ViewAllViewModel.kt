package com.dhimasdewanto.githubstars.view.view_all

import androidx.lifecycle.ViewModel
import com.dhimasdewanto.githubstars.core.lazyDeferred
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

class ViewAllViewModel(
    private val githubStarsRepo: GithubStarsRepo
) : ViewModel() {
    val listGithubStars by lazyDeferred {
        githubStarsRepo.getGithubStars()
    }

    suspend fun loadMoreData() {
        githubStarsRepo.loadMoreData()
    }
}
