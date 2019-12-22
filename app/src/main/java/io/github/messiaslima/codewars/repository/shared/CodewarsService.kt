package io.github.messiaslima.codewars.repository.shared

import io.github.messiaslima.codewars.entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CodewarsService {

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<User>

}