package io.github.messiaslima.codewars.repository.challenge

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import io.reactivex.Single

interface ChallengeRepository {

    fun findChallengesFromAPI(
        user: User,
        challengeType: ChallengeType,
        page: Int = 0
    ): Single<List<Challenge>>

    fun findChallenges(
        user: User,
        challengeType: ChallengeType,
        page: Int = 0
    ): LiveData<PagedList<Challenge>>

    fun findChallenge(id: String?): Single<Challenge>
}