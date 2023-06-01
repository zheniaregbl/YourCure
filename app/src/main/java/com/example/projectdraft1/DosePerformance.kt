package com.example.projectdraft1

data class DosePerformance(
    val time: String,
    val amount: Int,
    val stringDose: String
) : Comparable<DosePerformance> {
    override fun compareTo(other: DosePerformance): Int {
        val hour = this.time.substring(0, 2).toInt()
        val minute = this.time.substring(3, this.time.length).toInt()
        val hourOther = other.time.substring(0, 2).toInt()
        val minuteOther = other.time.substring(3, other.time.length).toInt()

        return if (hour == hourOther) {
            minute - minuteOther
        } else {
            hour - hourOther
        }
    }
}
