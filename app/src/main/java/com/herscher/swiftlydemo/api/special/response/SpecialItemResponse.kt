package com.herscher.swiftlydemo.api.special.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SpecialItemResponse(
    @Json(name = "display_name") val name: String,
    @Json(name = "height") val height: Int,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "original_price") val originalPrice: String,
    @Json(name = "price") val price: String,
    @Json(name = "width") val width: Int,
)