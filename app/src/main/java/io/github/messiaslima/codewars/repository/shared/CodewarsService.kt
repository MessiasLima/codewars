package io.github.messiaslima.codewars.repository.shared

import io.github.messiaslima.codewars.entity.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CodewarsService {

    @GET("users/{username}")
    fun searchUser(@Path("username") username: String): Single<User>

}