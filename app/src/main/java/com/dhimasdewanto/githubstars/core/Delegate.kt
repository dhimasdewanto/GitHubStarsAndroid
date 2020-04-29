package com.dhimasdewanto.githubstars.core

import kotlinx.coroutines.*

/**
 * Create val only after it needed.
 */
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}