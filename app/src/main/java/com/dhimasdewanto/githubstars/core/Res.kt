package com.dhimasdewanto.githubstars.core

sealed class Res<out T, out E>
class Ok<out T>(val value: T): Res<T, Nothing>()
class Err<out E>(val error: E): Res<Nothing, E>()