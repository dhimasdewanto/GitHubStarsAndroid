package com.dhimasdewanto.githubstars.view.view_all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimasdewanto.githubstars.core.Err
import com.dhimasdewanto.githubstars.core.Ok
import com.dhimasdewanto.githubstars.core.Res
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

class ViewAllViewModel(
    private val githubStarsRepo: GithubStarsRepo
) : ViewModel() {
    private var currentPage: Int = 1

    private val listGithubStars = mutableListOf<GitHubStars>()
    private val _downloadedGithubStars = MutableLiveData<List<GitHubStars>>()
    val downloadedGitHubStars: LiveData<List<GitHubStars>>
        get() = _downloadedGithubStars

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    suspend fun fetchGithubStars(): Res<Unit, String> {
        if (isLoading.value == true) {
            return Err("isLoading is true")
        }

        currentPage++
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

    private suspend fun getListGithubStars() = githubStarsRepo.getListGithubStars(currentPage)
}
