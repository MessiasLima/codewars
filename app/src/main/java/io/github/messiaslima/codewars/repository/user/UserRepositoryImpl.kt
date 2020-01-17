package io.github.messiaslima.codewars.repository.user

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.common.api.ApiErrorResponse
import io.github.messiaslima.codewars.repository.common.api.ApiSuccessResponse
import io.github.messiaslima.codewars.repository.user.datasource.UserAPIDataSource
import io.github.messiaslima.codewars.repository.user.datasource.UserLocalDataSource
import io.github.messiaslima.codewars.util.Resource
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAPIDataSouce: UserAPIDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override fun searchUser(username: String): LiveData<Resource<User>> {

        val apiResponse = userAPIDataSouce.searchUser(username)

        val userResource = MediatorLiveData<Resource<User>>()

        userResource.addSource(apiResponse) { response ->
            when(response) {

                is ApiSuccessResponse -> {
                    val user = response.body
                    saveUser(user)
                    userResource.value = Resource.success(response.body)
                }

                is ApiErrorResponse -> {
                    userResource.value = Resource.error(
                        msg = response.errorMessage,
                        data = null,
                        throwable = response.throwable
                    )
                }
            }
        }

        userResource.value = Resource.loading(null)

        return userResource
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

    override fun findSavedUsers(sortByHonor: Boolean, limit: Int): LiveData<Resource<List<User>>> {

        var users = userLocalDataSource.findLastUsers(limit)

        if (sortByHonor) {
            users = sortUsersByHonor(users)
        }

        return users.map {
            Resource.success(it)
        }
    }


    private fun sortUsersByHonor(usersLiveData: LiveData<List<User>>) = usersLiveData.map { users ->
        users.sortedByDescending { it.honor }
    }
}