package io.github.messiaslima.codewars.repository.user

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.common.api.ApiResponse
import io.github.messiaslima.codewars.repository.common.api.ApiSuccessResponse
import io.github.messiaslima.codewars.repository.user.datasource.UserAPIDataSource
import io.github.messiaslima.codewars.repository.user.datasource.UserLocalDataSource
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAPIDataSouce: UserAPIDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override fun searchUserV2(username: String): LiveData<ApiResponse<User>> {
        return userAPIDataSouce.searchUserV2(username).map {
            val user = (it as ApiSuccessResponse).body
            saveUser(user)
            it
        }
    }

    @WorkerThread
    private fun saveUser(user: User) {
        Executors.newSingleThreadExecutor().execute {
            userLocalDataSource.save(addCreationDate(user))
        }
    }

    private fun addCreationDate(user: User) = user.apply {
        creationDate = Date()
    }

    override fun findSavedUsers(limit: Int): LiveData<List<User>> {
        return userLocalDataSource.findLastUsers(limit)
    }
}