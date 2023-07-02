package com.example.myapplication212
data class Exercise(
    val name: String,
    val description: String,
    val imageResId: Int,
    var repetitions: Int = 0,
    var dayCompleted: String? = null,
    val history: MutableList<ExerciseHistory> = mutableListOf()
)

data class ExerciseHistory(
    val date: String,
    val repetitions: Int
)
