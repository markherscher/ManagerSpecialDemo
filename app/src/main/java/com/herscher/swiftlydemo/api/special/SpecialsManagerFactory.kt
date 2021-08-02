package com.herscher.swiftlydemo.api.special

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// This would be provided via Dagger in a real app
class SpecialsManagerFactory {
    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/Swiftly-Systems/"

        fun create(): SpecialsManager {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(OkHttpClient.Builder().build())
                .build()

            return RealSpecialsManager(retrofit.create(SpecialsApi::class.java))
        }
    }
}