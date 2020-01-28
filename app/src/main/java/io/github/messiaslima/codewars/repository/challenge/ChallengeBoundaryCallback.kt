package io.github.messiaslima.codewars.repository.challenge

import androidx.paging.PagedList
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.challenges.ChallengeType

class ChallengeBoundaryCallback(
    private val user: User,
    private val challengeType: ChallengeType,
    private val page: Int
) : PagedList.BoundaryCallback<Challenge>() {

    var loadChallengesFromAPI: ((
        user: User,
        challengeType: ChallengeType,
        page: Int
    ) -> Unit)? = null

    override fun onZeroItemsLoaded() {
        loadChallengesFromAPI?.invoke(user, challengeType, 0)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
        loadChallengesFromAPI?.invoke(user, challengeType, page)
    }
}