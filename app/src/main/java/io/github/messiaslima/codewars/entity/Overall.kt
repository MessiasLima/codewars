package io.github.messiaslima.codewars.entity

import java.io.Serializable

data class Overall(
    val rank: Long? = null,
    val name: String? = null,
    val color: Color? = null,
    val score: Long? = null
) : Serializable