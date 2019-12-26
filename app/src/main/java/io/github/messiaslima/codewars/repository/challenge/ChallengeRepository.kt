package io.github.messiaslima.codewars.repository.challenge

import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import io.reactivex.Single

interface ChallengeRepository {
    fun findChallenges(user: User, challengeType: ChallengeType, page: Int = 0): Single<List<Challenge>>
}