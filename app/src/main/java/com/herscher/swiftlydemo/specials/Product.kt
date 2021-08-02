package com.herscher.swiftlydemo.specials

data class Product(
    val name: String,
    val imageUrl: String,
    val currentPrice: String,
    val originalPrice: String,
    val dimension: Dimension,
) {
    data class Dimension(
        val heightPercentage: Float,
        val widthPercentage: Float
    )
}