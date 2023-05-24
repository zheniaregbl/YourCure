package com.example.projectdraft1

//Класс для создания элемента списка экрана Сегодня

data class MedicationDose (
    val doseId: Int,
    val medicationId: Int,
    val title: String,
    val imageId: Int,
    val time: String,
    val amount: Int,
    val stringDose: String
) : Comparable<MedicationDose> {
    override fun compareTo(other: MedicationDose): Int {
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
