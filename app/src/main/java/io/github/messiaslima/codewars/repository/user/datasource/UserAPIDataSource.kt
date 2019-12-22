package io.github.messiaslima.codewars.repository.user.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.shared.CodewarsResult
import io.github.messiaslima.codewars.repository.shared.CodewarsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserAPIDataSource @Inject constructor(
    private val codewarsService: CodewarsService
) {

    fun searchUser(username: String): LiveData<CodewarsResult<User>> {

        val result = MutableLiveData<CodewarsResult<User>>()

        codewarsService.getUser(username).enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                result.value = CodewarsResult.Error("", t)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                result.value = CodewarsResult.Success(response.body() as User)
            }

        })

        return result
    }

}