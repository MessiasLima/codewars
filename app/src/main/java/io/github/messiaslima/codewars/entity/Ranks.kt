package io.github.messiaslima.codewars.entity

data class Ranks(
    val overall: Overall? = null,
    val languages: Map<String, Overall>? = null
)