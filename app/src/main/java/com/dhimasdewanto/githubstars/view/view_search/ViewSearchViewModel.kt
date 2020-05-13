package com.dhimasdewanto.githubstars.view.view_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimasdewanto.githubstars.core.Err
import com.dhimasdewanto.githubstars.core.Ok
import com.dhimasdewanto.githubstars.core.Res
import com.dhimasdewanto.githubstars.core.lazyDeferred
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

class ViewSearchViewModel(
    private val githubStarsRepo: GithubStarsRepo
) : ViewModel() {
    private var currentPage: Int = 1
    private var searchText: String = ""

    private val listGithubStars = mutableListOf<GitHubStars>()
    private val _downloadedGithubStars = MutableLiveData<List<GitHubStars>>()
    val downloadedGitHubStars: LiveData<List<GitHubStars>>
        get() = _downloadedGithubStars

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    suspend fun startSearching(searchText: String): Res<Unit, String> {
        if (isLoading.value == true) {
            return Err("isLoading is true")
        }

        this.searchText = searchText
        currentPage = 1
        listGithubStars.clear()
        _downloadedGithubStars.value = listGithubStars
        return fetchGithubStars()
    }

    suspend fun loadMoreData(): Res<Unit, String> {
        if (isLoading.value == true) {
            return Err("isLoading is true")
        }

        currentPage++
        return fetchGithubStars()
    }

    private suspend fun fetchGithubStars(): Res<Unit, String> {
        _isLoading.value = true

        return when (val result = getListGithubStars()) {
            is Ok -> {
                val githubStars = result.value
                listGithubStars.addAll(githubStars)
                _downloadedGithubStars.value = listGithubStars
                _isLoading.value = false
                Ok(Unit)
            }
            is Err -> {
                _isLoading.value = false
                result
            }
        }
    }

    private suspend fun getListGithubStars() =
        githubStarsRepo.getListGithubStars(currentPage, searchText)
}
