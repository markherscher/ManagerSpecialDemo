package com.herscher.swiftlydemo.api

import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Based on https://stackoverflow.com/a/53663579
fun <T> Call<T>.toNetworkSingle(): Single<NetworkResult<T, NetworkError>> {
    val single = SingleSubject.create<NetworkResult<T, NetworkError>>()
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            when (t) {
                // Best guesses at no Internet
                is UnknownHostException -> single.onSuccess(NetworkResult.Failed(NetworkError.NoInternet))
                is SocketTimeoutException -> single.onSuccess(NetworkResult.Failed(NetworkError.Timeout))
                else -> single.onError(t)
            }
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val body = response.body()
            if (body == null) {
                single.onSuccess(NetworkResult.Failed(NetworkError.FailedResponse))
            } else {
                single.onSuccess(NetworkResult.Success(body))
            }
        }
    })

    return single
}
