package com.dhimasdewanto.githubstars.view.screens.main.view_search

import com.dhimasdewanto.githubstars.core.Err
import com.dhimasdewanto.githubstars.core.Ok
import com.dhimasdewanto.githubstars.core.mvi.ScopeViewModel
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsParams
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsUseCase

class ViewSearchViewModel(
    private val useCase: GetListGitHubStarsUseCase
) : ScopeViewModel<ViewSearchState, ViewSearchIntent>(ViewSearchState.Initial) {

    override suspend fun handleIntent(intent: ViewSearchIntent) {
        when(intent) {
            ViewSearchIntent.LoadMoreData -> loadMoreData()
            is ViewSearchIntent.StartSearching -> startSearching(intent.searchText)
        }
    }

    suspend fun startSearching(searchText: String) {
        if (state is ViewSearchState.Loading) {
            return
        }

        state = ViewSearchState.Loading
        fetchGithubStars(searchText, 1)
    }

    suspend fun loadMoreData() {
        if (state is ViewSearchState.ShowResult) {
            val stateValue = state as ViewSearchState.ShowResult
            val search = stateValue.search
            val page = stateValue.page + 1
            val list = stateValue.listGithubStars

            state = ViewSearchState.LoadingMoreData
            fetchGithubStars(search, page, list)
        }
    }

    private suspend fun fetchGithubStars(
        search: String,
        page: Int,
        listGithubStars: List<GitHubStars> = emptyList()
    ) {
        state = when (val result = getListGithubStars(page, search)) {
            is Err -> ViewSearchState.Error(result.error.message)
            is Ok -> {
                // NOT REVERSED!
                val githubStars = listGithubStars + result.value

                ViewSearchState.ShowResult(githubStars, search, page)
            }
        }
    }

    private suspend fun getListGithubStars(page: Int, search: String) =
        useCase.call(GetListGitHubStarsParams(page, search))

}
