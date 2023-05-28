package com.example.projectdraft1

import android.text.InputFilter
import android.text.Spanned

class InputFilterFloat(
    private val min: Float,
    private val max: Float
): InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): String? {
        try{
            val input = (dest.toString() + source.toString()).toFloat()
            if (isInRange(min, max, input)) return null
        } catch (_: NumberFormatException) {
        }

        return ""
    }

    private fun isInRange(a: Float, b: Float, c: Float): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}