package io.github.messiaslima.codewars.entity

import java.util.*

data class Challenge (
    val id: String,
    val name: String,
    val slug: String,
    val completedLanguages: List<Language>,
    val completedAt: Date
)