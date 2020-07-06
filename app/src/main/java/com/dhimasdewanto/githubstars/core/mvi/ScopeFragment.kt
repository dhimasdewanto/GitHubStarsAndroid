package com.dhimasdewanto.githubstars.core.mvi

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

abstract class ScopeFragment<State : IState, Intent : IIntent> : Fragment() {
    private lateinit var intentChannel: Channel<Intent>

    protected fun setViewModelState(state: LiveData<State>) {
        state.observe(this, Observer {
            handleState(it)
        })
    }

    protected fun setIntentChannel(ic: Channel<Intent>) {
        intentChannel = ic
    }

    protected fun sendIntent(intent: Intent) {
        lifecycleScope.launch {
            intentChannel.send(intent)
        }
    }

    abstract fun handleState(state: State)
}