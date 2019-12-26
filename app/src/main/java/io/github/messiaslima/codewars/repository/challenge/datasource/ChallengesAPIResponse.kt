package io.github.messiaslima.codewars.repository.challenge.datasource

import io.github.messiaslima.codewars.entity.Challenge

data class ChallengesAPIResponse (
    val totalPages: Long,
    val totalItems: Long,
    val data: List<Challenge>
)