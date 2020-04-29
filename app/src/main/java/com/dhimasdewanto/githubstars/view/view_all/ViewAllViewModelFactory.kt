package com.dhimasdewanto.githubstars.view.view_all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.githubstars.domain.usecases.GetAllUseCase

class ViewAllViewModelFactory(
    private val getAllUseCase: GetAllUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>) : T {
        return ViewAllViewModel(getAllUseCase) as T
    }
}