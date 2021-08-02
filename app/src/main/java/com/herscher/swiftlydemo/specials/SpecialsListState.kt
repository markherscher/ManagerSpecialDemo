package com.herscher.swiftlydemo.specials

sealed class SpecialsListState {
    object Initial : SpecialsListState()

    object Loading : SpecialsListState()

    data class Content(val products: List<Product>) : SpecialsListState()

    data class Error(val text: String) : SpecialsListState()
}