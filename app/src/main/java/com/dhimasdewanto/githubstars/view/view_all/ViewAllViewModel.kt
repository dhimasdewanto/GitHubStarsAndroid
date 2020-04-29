package com.dhimasdewanto.githubstars.view.view_all

import androidx.lifecycle.ViewModel
import com.dhimasdewanto.githubstars.core.lazyDeferred
import com.dhimasdewanto.githubstars.domain.usecases.GetAllUseCase
import com.dhimasdewanto.githubstars.domain.usecases.GetAllUseCaseParams

class ViewAllViewModel(
    private val getAllUseCase: GetAllUseCase
) : ViewModel() {
    val listGithubStars by lazyDeferred {
        getAllUseCase.call(GetAllUseCaseParams(1))
    }
}
