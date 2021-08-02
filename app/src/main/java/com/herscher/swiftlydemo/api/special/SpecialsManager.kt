package com.herscher.swiftlydemo.api.special

import com.herscher.swiftlydemo.api.NetworkError
import com.herscher.swiftlydemo.api.NetworkResult
import io.reactivex.Single

interface SpecialsManager {
    fun getSpecialsList(): Single<SpecialsManagerResult>
}

typealias SpecialsManagerResult = NetworkResult<ManagerSpecials, NetworkError>