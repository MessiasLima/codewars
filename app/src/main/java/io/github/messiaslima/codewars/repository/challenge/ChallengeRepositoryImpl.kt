package io.github.messiaslima.codewars.repository.challenge

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengeAPIDataSource
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengeLocalDataSource
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengesAPIResponse
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import io.reactivex.Single
import androidx.paging.toLiveData
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeAPIDataSource: ChallengeAPIDataSource,
    private val challengeLocalDataSource: ChallengeLocalDataSource
) : ChallengeRepository {

    override fun findChallenges(user: User, challengeType: ChallengeType, page: Int): Single<List<Challenge>> {

        val responseSingle = when(challengeType){
            ChallengeType.COMPLETED -> challengeAPIDataSource.findCompletedChallenges(user, page)
            ChallengeType.AUTHORED -> challengeAPIDataSource.findAuthoredChallenges(user)
        }

        return handleResponse(responseSingle)
    }

    override fun findChallengesV2(): LiveData<PagedList<Challenge>> {

        val config = Config(
            pageSize = 10,
            enablePlaceholders = false,
            maxSize = 50
        )

        return challengeLocalDataSource.findAll().toLiveData(config)
    }

    private fun handleResponse(responseSingle: Single<ChallengesAPIResponse>): Single<List<Challenge>> {
        return responseSingle.map { it.data }
    }

    override fun findChallenge(id: String?): Single<Challenge> {
        return challengeAPIDataSource.findChallenge(id)
    }


}