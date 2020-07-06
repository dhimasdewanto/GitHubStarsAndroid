package com.dhimasdewanto.githubstars.view.screens.main.view_all

import com.dhimasdewanto.githubstars.core.Err
import com.dhimasdewanto.githubstars.core.Ok
import com.dhimasdewanto.githubstars.core.mvi.ScopeViewModel
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsParams
import com.dhimasdewanto.githubstars.domain.usecases.GetListGitHubStarsUseCase

class ViewAllViewModel(
    private val useCase: GetListGitHubStarsUseCase
) : ScopeViewModel<ViewAllState, ViewAllIntent>(ViewAllState.Initial) {

    override suspend fun handleIntent(intent: ViewAllIntent) {
        when (intent) {
            ViewAllIntent.StartFetching -> startFetching()
            ViewAllIntent.LoadMoreData -> loadMoreData()
        }
    }

    private suspend fun startFetching() {
        if (state is ViewAllState.Initial) {
            state = ViewAllState.LoadingMoreData
            addListGithubStars(1)
        }
    }

    private suspend fun loadMoreData() {
        if (state is ViewAllState.ShowResult) {
            val stateValue = (state as ViewAllState.ShowResult)
            val newPage = stateValue.page + 1
            val currentListGithubStars = stateValue.listGithubStars

            state = ViewAllState.LoadingMoreData
            addListGithubStars(newPage, currentListGithubStars)
        }
    }

    private suspend fun addListGithubStars(
        page: Int,
        listGithubStars: List<GitHubStars> = emptyList()
    ) {
        state = when (val result = useCase.call(GetListGitHubStarsParams(page))) {
            is Err -> ViewAllState.Error(result.error.message)
            is Ok -> {
                // NOT REVERSED!
                val newListGithubStars = listGithubStars + result.value

                ViewAllState.ShowResult(newListGithubStars, page)
            }
        }
    }

}
