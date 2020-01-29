package io.github.messiaslima.codewars.repository.challenge.datasource

import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.common.api.CodewarsService
import javax.inject.Inject

class ChallengeAPIDataSource @Inject constructor(
    private val service: CodewarsService
) {
    fun findCompletedChallenges(user: User, page: Int) = service.findCompletedChallenges(user.username, page)

    fun findAuthoredChallenges(user: User) = service.findAuthoredChallenges(user.username)

    fun findChallenge(id: String?) = service.findChallenge(id)
}