package com.herscher.swiftlydemo.util

import android.support.annotation.PluralsRes
import android.support.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes id: Int): String

    fun getFormattedString(@StringRes id: Int, vararg formatArgs: Any?): String

    fun getPluralString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any?): String
}