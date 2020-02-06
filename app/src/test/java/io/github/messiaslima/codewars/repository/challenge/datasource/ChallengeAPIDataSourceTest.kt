package io.github.messiaslima.codewars.repository.challenge.datasource

import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.common.api.CodewarsService
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import okhttp3.Request
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ChallengeAPIDataSourceTest {

    @Mock
    lateinit var codewarsService: CodewarsService
    lateinit var challengeDataSource: ChallengeAPIDataSource
    private val username = "username"
    private val page = 0
    private val compositeDisposable = CompositeDisposable()

    @Before
    fun setup(){
        challengeDataSource = ChallengeAPIDataSource(codewarsService)
    }

    @After
    fun cleanup(){
        compositeDisposable.clear()
    }

    @Test
    fun findCompletedChallenges() {

        val dummyChallenges = getDummyChallengesApiResponse()

        `when`(codewarsService.findCompletedChallenges(username, page)).thenReturn(
            getDummyResponse(dummyChallenges)
        )

        val user = User(username = username)

        val response = challengeDataSource.findCompletedChallenges(user, page).execute()

        assertTrue(response.isSuccessful)
        assertEquals(response.body(), dummyChallenges)
    }

    @Test
    fun findAuthoredChallenges() {

        val dummyChallenges = getDummyChallengesApiResponse()

        `when`(codewarsService.findAuthoredChallenges(username)).thenReturn(
            getDummyResponse(dummyChallenges)
        )

        val user = User(username = username)
        val response = challengeDataSource.findAuthoredChallenges(user).execute()
        assertTrue(response.isSuccessful)
        assertEquals(response.body(), dummyChallenges)
    }

    @Test
    fun findChallenge() {
        val challengeId = UUID.randomUUID().toString()
        val challenge = Challenge(
            id = challengeId,
            name = "Some random name"
        )
        `when`(codewarsService.findChallenge(challengeId)).thenReturn(Single.just(challenge))

        challengeDataSource.findChallenge(challengeId).subscribe { response ->
            assertEquals(response, challenge)
        }.addTo(compositeDisposable)
    }

    private fun getDummyChallengesApiResponse(): ChallengesAPIResponse {

        val challenges = listOf(
            Challenge(id = "a"),
            Challenge(id = "b"),
            Challenge(id = "c")
        )

        return ChallengesAPIResponse(
            totalItems = challenges.size.toLong(),
            totalPages = 1,
            data = challenges
        )
    }

    private fun <T> getDummyResponse(response: T) = object : Call<T> {
        override fun enqueue(callback: Callback<T>) {}
        override fun isExecuted() = false
        override fun clone() = this
        override fun isCanceled() = false
        override fun cancel() {}
        override fun execute() = Response.success(response)
        override fun request() = Request.Builder().build()
    }
}