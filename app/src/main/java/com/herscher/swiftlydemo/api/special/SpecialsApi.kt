package com.herscher.swiftlydemo.api.special

import com.herscher.swiftlydemo.api.special.response.SpecialsResponse
import retrofit2.Call
import retrofit2.http.GET

internal interface SpecialsApi {
    @GET("code-exercise-android/master/backup")
    fun getSpecials(): Call<SpecialsResponse>
}