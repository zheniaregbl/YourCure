package com.example.projectdraft1

//Класс для создания элемента списка экрана Сегодня

data class MedicationDose(
    val medicationId: Int,
    val title: String,
    val imageId: Int,
    val time: String,
    val amount: Int
)
