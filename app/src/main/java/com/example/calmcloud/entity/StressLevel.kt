package com.example.calmcloud.entity

data class StressLevel(
    val id: String,
    val date: String,
    val stressLevels: List<StressLevelType>
)

enum class StressLevelType {
    NO_STRESS, VERY_LOW_STRESS, LOW_STRESS, MODERATE_STRESS, HIGH_STRESS, VERY_HIGH_STRESS, EXTREME_STRESS
}

