package io.github.messiaslima.codewars.repository.challenge

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengeAPIDataSource
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengeLocalDataSource
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import io.reactivex.Single
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeAPIDataSource: ChallengeAPIDataSource,
    private val challengeLocalDataSource: ChallengeLocalDataSource
) : ChallengeRepository {

    private fun findChallengesFromAPI(
        user: User,
        challengeType: ChallengeType,
        page: Int
    ): List<Challenge>? {

        val call = when (challengeType) {
            ChallengeType.COMPLETED -> challengeAPIDataSource.findCompletedChallenges(user, page)
            ChallengeType.AUTHORED -> challengeAPIDataSource.findAuthoredChallenges(user)
        }

        val response = call.execute()

        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }

    private fun saveChallenge(user: User, type: ChallengeType, challenges: List<Challenge>) {
        challenges.forEach {
            it.owner = user.username
            it.type = type
        }
        challengeLocalDataSource.save(challenges)
    }

    override fun findChallenges(
        user: User,
        challengeType: ChallengeType
    ): LiveData<PagedList<Challenge>> {

        val config = Config(pageSize = 10, enablePlaceholders = false, maxSize = 50)

        val boundaryCallback = ChallengeBoundaryCallback(
            loadChallengesFromAPI = { page -> findChallengesFromAPI(user, challengeType, page) },
            saveChallenges = { challenges -> saveChallenge(user, challengeType, challenges) }
        )

        return challengeLocalDataSource.find(user.username, challengeType).toLiveData(
            config = config,
            boundaryCallback = boundaryCallback
        )
    }

    override fun findChallenge(id: String?): Single<Challenge> {
        return challengeAPIDataSource.findChallenge(id)
    }

}