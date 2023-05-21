package com.example.projectdraft1

data class Medication(
    val medicationId: Int,
    val imageId: Int,
    val title: String,
    val dose: String,
    val days: Int,
    val acceptDose: Int
)