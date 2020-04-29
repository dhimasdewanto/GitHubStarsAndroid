package com.dhimasdewanto.githubstars.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface  UseCase<Type, Params> {
    suspend fun call(params: Params) : Type

    suspend fun getIOContext(params: Type) : Type {
        return withContext(Dispatchers.IO) {
            return@withContext params
        }
    }
}

