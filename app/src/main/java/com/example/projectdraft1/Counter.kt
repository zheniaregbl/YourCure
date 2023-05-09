package com.example.projectdraft1

class Counter(_sum: Int, _maxSum: Int, _minSum: Int) {
    private var sum: Int
    private var maxSum: Int
    private var minSum: Int

    init{
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

    operator fun inc(): Counter{
        return Counter(sum + 1, maxSum, minSum)
    }

    operator fun dec(): Counter{
        return Counter(sum - 1, maxSum, minSum)
    }

    override fun toString(): String {
        return sum.toString()
    }
}