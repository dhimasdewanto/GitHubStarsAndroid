package com.dhimasdewanto.githubstars.view.view_all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsUseCase

@Suppress("UNCHECKED_CAST")
class ViewAllViewModelFactory(
    private val useCase: GetListGitHubStarsUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>) : T {
        return ViewAllViewModel(useCase) as T
    }
}