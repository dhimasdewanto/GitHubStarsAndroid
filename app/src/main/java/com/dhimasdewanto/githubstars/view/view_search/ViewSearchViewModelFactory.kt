package com.dhimasdewanto.githubstars.view.view_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

@Suppress("UNCHECKED_CAST")
class ViewSearchViewModelFactory(
    private val githubStarsRepo: GithubStarsRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>) : T {
        return ViewSearchViewModel(githubStarsRepo) as T
    }
}