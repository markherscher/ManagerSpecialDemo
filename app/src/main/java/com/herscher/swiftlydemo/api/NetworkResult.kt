package com.herscher.swiftlydemo.api

sealed class NetworkResult<out T, out F> {
    data class Success<T>(val data: T) : NetworkResult<T, Nothing>()

    data class Failed<F>(val failure: F): NetworkResult<Nothing, F>()
}