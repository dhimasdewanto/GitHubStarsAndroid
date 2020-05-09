package com.dhimasdewanto.githubstars.view.view_all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

@Suppress("UNCHECKED_CAST")
class ViewAllViewModelFactory(
    private val githubStarsRepo: GithubStarsRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>) : T {
        return ViewAllViewModel(githubStarsRepo) as T
    }
}