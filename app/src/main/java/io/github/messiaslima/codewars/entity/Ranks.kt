package io.github.messiaslima.codewars.entity

import androidx.room.Embedded

data class Ranks(

    @Embedded
    val overall: Overall? = null,

    val languages: Map<String, Overall>? = null
)