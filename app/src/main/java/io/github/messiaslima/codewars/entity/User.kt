package io.github.messiaslima.codewars.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @NonNull
    @PrimaryKey
    val username: String = "",

    val name: String? = null,

    val honor: Long? = null,

    val clan: String? = null,

    val leaderboardPosition: Long? = null,

    val skills: List<String>? = null,

    @Embedded(prefix = "ranks_")
    val ranks: Ranks? = null,

    @Embedded
    val codeChallenges: CodeChallenges? = null
)