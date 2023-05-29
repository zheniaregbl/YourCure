package com.example.projectdraft1

class FloatCounter(_sum: Float, _maxSum: Float, _minSum: Float) {
    private var sum: Float
    private var maxSum: Float
    private var minSum: Float

    init {
        sum = _sum
        maxSum = _maxSum
        minSum = _minSum
    }

    fun haveMinSize(): Boolean{
        return sum == minSum
    }

    fun haveMaxSize(): Boolean{
        return sum == maxSum
    }

    operator fun inc(): FloatCounter{
        return FloatCounter(sum + 1, maxSum, minSum)
    }

    operator fun dec(): FloatCounter{
        return FloatCounter(sum - 1, maxSum, minSum)
    }

    override fun toString(): String {
        return sum.toString()
    }
}