package com.example.calmcloud.entity

data class Mood(
    val id: String,
    val date: String,
    val moodType: MoodType
)

enum class MoodType {
    HAPPY, THANKFUL, ENERGETIC, CALM, INDIFFERENT, TIRED, SAD, ANXIOUS, ANGRY
}
