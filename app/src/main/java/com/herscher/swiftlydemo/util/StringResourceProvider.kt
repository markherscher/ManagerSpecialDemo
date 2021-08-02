package com.herscher.swiftlydemo.util

import android.content.Context

/**
 * A [StringProvider] that uses Android's resource system to provide its values.
 */
class StringResourceProvider(
    private val context: Context
): StringProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getFormattedString(id: Int, vararg formatArgs: Any?): String {
        return context.getString(id, *formatArgs)
    }

    override fun getPluralString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        return context.resources.getQuantityString(id, quantity, *formatArgs)
    }
}