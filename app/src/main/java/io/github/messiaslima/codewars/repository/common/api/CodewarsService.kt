package io.github.messiaslima.codewars.repository.common.api

import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengesAPIResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodewarsService {

    @GET("users/{username}")
    fun searchUser(@Path("username") username: String): Single<User>

    @GET("users/{username}/code-challenges/completed")
    fun findCompletedChallenges(
        @Path("username") username: String,
        @Query("page") page: Int
    ): Single<ChallengesAPIResponse>

    @GET("users/{username}/code-challenges/authored")
    fun findAuthoredChallenges(@Path("username") username: String): Single<ChallengesAPIResponse>

    @GET("code-challenges/{id}")
    fun findChallenge(@Path("id") id: String?): Single<Challenge>

}