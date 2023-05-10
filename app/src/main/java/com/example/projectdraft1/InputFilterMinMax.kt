package com.example.projectdraft1

import android.text.InputFilter
import android.text.Spanned

class InputFilterMinMax(): InputFilter {
    private var min = 1
    private var max = 10

    constructor(_min: Int, _max: Int) : this(){
        min = _min
        max = _max
    }

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): String? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(min, max, input)) return null
        } catch (_: NumberFormatException) {
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}