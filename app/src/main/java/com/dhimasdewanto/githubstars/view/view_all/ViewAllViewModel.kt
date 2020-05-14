package com.dhimasdewanto.githubstars.view.view_all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimasdewanto.githubstars.core.Err
import com.dhimasdewanto.githubstars.core.Ok
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsParams
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsUseCase

class ViewAllViewModel(
    private val useCase: GetListGitHubStarsUseCase
) : ViewModel() {
    private val _state = MutableLiveData<ViewAllState>(ViewAllState.Initial)
    val state: LiveData<ViewAllState>
        get() = _state

    suspend fun startFetching() {
        if (state.value is ViewAllState.Initial) {
            _state.value = ViewAllState.LoadingMoreData
            addListGithubStars(1)
        }
    }

    suspend fun loadMoreData() {
        if (state.value is ViewAllState.ShowResult) {
            val stateValue = (state.value as ViewAllState.ShowResult)
            val newPage = stateValue.page + 1
            val currentListGithubStars = stateValue.listGithubStars

            _state.value = ViewAllState.LoadingMoreData
            addListGithubStars(newPage, currentListGithubStars)
        }
    }

    private suspend fun addListGithubStars(
        page: Int,
        listGithubStars: List<GitHubStars> = emptyList()
    ) {
        when (val result = useCase.call(GetListGitHubStarsParams(page))) {
            is Err -> _state.value = ViewAllState.Error(result.error.message)
            is Ok -> {
                // NOT REVERSED!
                val newListGithubStars = listGithubStars + result.value

                _state.value = ViewAllState.ShowResult(newListGithubStars, page)
            }
        }
    }
}
