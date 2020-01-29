package io.github.messiaslima.codewars.repository.challenge

import androidx.paging.PagedList
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class ChallengeBoundaryCallback(
    private val loadChallengesFromAPI: ((page: Int) -> List<Challenge>?),
    private val saveChallenges: ((challenges: List<Challenge>)-> Unit)
) : PagedList.BoundaryCallback<Challenge>() {

    private var page = 0


    override fun onZeroItemsLoaded() {
        getChallengesAndSave(0)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
        getChallengesAndSave(page)
    }

    private fun getChallengesAndSave(currentPage: Int) {
        Executors.newSingleThreadExecutor().execute {
            val challenges = loadChallengesFromAPI.invoke(currentPage)
            challenges?.let {
                page++
                saveChallenges.invoke(it)
            }
        }
    }
}