package io.github.messiaslima.codewars.entity

import androidx.room.Embedded
import java.io.Serializable

data class Ranks(

    @Embedded
    val overall: Rank? = null,

    val languages: Map<String, Rank>? = null
): Serializable