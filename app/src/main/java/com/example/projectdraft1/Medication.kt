package com.example.projectdraft1

data class Medication(
    val medicationId: Int,
    val imageId: Int,
    val title: String,
    val dateStart: String,
    val dose: String,
    val days: Int,
    val daysPass: Int,
    val acceptDose: Int
)