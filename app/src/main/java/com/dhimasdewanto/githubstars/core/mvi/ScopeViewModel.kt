package com.dhimasdewanto.githubstars.core.mvi

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class ScopeViewModel<State : IState, Intent : IIntent>(
    private val initialState: State,
    val intentChannel: Channel<Intent> = Channel(Channel.UNLIMITED)
) : ViewModel() {
    private val _viewModelState = MutableLiveData<State>().apply {
        value = initialState
    }
    val viewModelState: LiveData<State>
        get() = _viewModelState

    var state: State
        get() = viewModelState.value!!
        set(value) {
            viewModelScope.launch(Dispatchers.Main) {
                _viewModelState.value = value
            }
        }

    init {
        initIntentHandler()
    }

    abstract suspend fun handleIntent(intent: Intent)

    private fun initIntentHandler() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                handleIntentWithIO(it)
            }
        }
    }

    private fun handleIntentWithIO(intent: Intent) {
        viewModelScope.launch(Dispatchers.IO) {
            handleIntent(intent)
        }
    }
}