package com.herscher.swiftlydemo.specials

import com.herscher.swiftlydemo.R
import com.herscher.swiftlydemo.util.StringProvider
import java.lang.IllegalArgumentException

class TestStringProvider: StringProvider {
    override fun getString(id: Int): String {
        return when (id) {
            R.string.error_no_internet -> "No Internet connection"
            R.string.error_general -> "An unexpected error occurred"
            else -> throw IllegalArgumentException("unknown string ID")
        }
    }

    override fun getFormattedString(id: Int, vararg formatArgs: Any?): String {
        return when (id) {
            R.string.dollar_format -> "$${formatArgs[0]}"
            else -> throw IllegalArgumentException("unknown string ID")
        }
    }

    override fun getPluralString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        throw IllegalArgumentException("plurals not handled")
    }
}