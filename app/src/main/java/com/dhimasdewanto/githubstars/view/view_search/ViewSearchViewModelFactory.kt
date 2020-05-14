package com.dhimasdewanto.githubstars.view.view_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsUseCase

@Suppress("UNCHECKED_CAST")
class ViewSearchViewModelFactory(
    private val useCase: GetListGitHubStarsUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>) : T {
        return ViewSearchViewModel(useCase) as T
    }
}