package com.example.calmcloud.entity

data class Sleep(
    val id: String,
    val date: String,
    val sleepDurationStart: String,
    val sleepDurationEnd: String,
    val sleepQuality: Int,
    val sleepFactors: String
)
