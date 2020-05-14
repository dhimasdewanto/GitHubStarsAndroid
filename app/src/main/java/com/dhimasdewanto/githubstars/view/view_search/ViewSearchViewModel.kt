package com.dhimasdewanto.githubstars.view.view_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimasdewanto.githubstars.core.Err
import com.dhimasdewanto.githubstars.core.Ok
import com.dhimasdewanto.githubstars.core.Res
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsParams
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsUseCase

class ViewSearchViewModel(
    private val useCase: GetListGitHubStarsUseCase
) : ViewModel() {
    private val _state = MutableLiveData<ViewSearchState>(ViewSearchState.Initial)
    val state: LiveData<ViewSearchState>
        get() = _state

    suspend fun startSearching(searchText: String) {
        if (state.value is ViewSearchState.Loading) {
            return
        }

        _state.value = ViewSearchState.Loading
        fetchGithubStars(searchText, 1)
    }

    suspend fun loadMoreData() {
        if (state.value is ViewSearchState.ShowResult) {
            val stateValue = state.value as ViewSearchState.ShowResult
            val search = stateValue.search
            val page = stateValue.page + 1
            val list = stateValue.listGithubStars

            _state.value = ViewSearchState.LoadingMoreData
            fetchGithubStars(search, page, list)
        }
    }

    private suspend fun fetchGithubStars(
        search: String,
        page: Int,
        listGithubStars: List<GitHubStars> = emptyList()
    ) {
        when (val result = getListGithubStars(page, search)) {
            is Err -> _state.value = ViewSearchState.Error(result.error.message)
            is Ok -> {
                // NOT REVERSED!
                val githubStars = listGithubStars + result.value

                _state.value = ViewSearchState.ShowResult(githubStars, search, page)
            }
        }
    }

    private suspend fun getListGithubStars(page: Int, search: String) =
        useCase.call(GetListGitHubStarsParams(page, search))
}
