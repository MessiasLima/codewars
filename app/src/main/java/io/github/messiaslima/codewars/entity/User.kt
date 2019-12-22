package io.github.messiaslima.codewars.entity

data class User(
    val username: String? = null,
    val name: String? = null,
    val honor: Long? = null,
    val clan: String? = null,
    val leaderboardPosition: Long? = null,
    val skills: List<String>? = null,
    val ranks: Ranks? = null,
    val codeChallenges: CodeChallenges? = null
)