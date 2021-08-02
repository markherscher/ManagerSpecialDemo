package com.herscher.swiftlydemo.api.special.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SpecialsResponse(
    @Json(name = "canvasUnit") val canvasUnit: Int,
    @Json(name = "managerSpecials") val itemList: List<SpecialItemResponse>,
)