package io.github.messiaslima.codewars.repository.challenge.datasource

import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.common.api.CodewarsService
import io.reactivex.Single
import javax.inject.Inject

class ChallengeAPIDataSource @Inject constructor(
    private val codewarsService: CodewarsService
) {
    fun findCompletedChallenges(user: User, page: Int): Single<ChallengesAPIResponse> {
        return codewarsService.findCompletedChallenges(user.username, page)
    }

    fun findAuthoredChallenges(user: User): Single<ChallengesAPIResponse> {
        return codewarsService.findAuthoredChallenges(user.username)
    }

    fun findChallenge(id: String?): Single<Challenge> {
        return codewarsService.findChallenge(id)
    }
}