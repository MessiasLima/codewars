package io.github.messiaslima.codewars.repository.challenge

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import io.reactivex.Single

interface ChallengeRepository {

    fun findChallenges(
        user: User,
        challengeType: ChallengeType
    ): LiveData<PagedList<Challenge>>

    fun findChallenge(id: String?): Single<Challenge>
}