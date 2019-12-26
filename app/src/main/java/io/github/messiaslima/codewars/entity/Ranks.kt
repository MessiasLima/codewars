package io.github.messiaslima.codewars.entity

import androidx.room.Embedded
import java.io.Serializable

data class Ranks(

    @Embedded
    val overall: Overall? = null,

    val languages: Map<String, Overall>? = null
): Serializable