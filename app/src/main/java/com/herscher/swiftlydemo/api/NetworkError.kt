package com.herscher.swiftlydemo.api

sealed class NetworkError {
    object NoInternet : NetworkError()

    object Timeout : NetworkError()

    object FailedResponse : NetworkError()
}