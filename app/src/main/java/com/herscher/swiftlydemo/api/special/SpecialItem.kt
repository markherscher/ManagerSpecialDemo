package com.herscher.swiftlydemo.api.special

data class SpecialItem(
    val name: String,
    val height: Int,
    val imageUrl: String,
    val originalPrice: String,
    val price: String,
    val width: Int,
)