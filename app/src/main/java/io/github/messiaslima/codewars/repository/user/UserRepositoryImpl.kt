package io.github.messiaslima.codewars.repository.user

import androidx.lifecycle.LiveData
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.user.datasource.UserAPIDataSource
import io.github.messiaslima.codewars.repository.user.datasource.UserLocalDataSource
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAPIDataSouce: UserAPIDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override fun searchUser(username: String): Single<User> {
        return userAPIDataSouce.searchUser(username)
            .map(this::addCreationDate)
            .flatMap(this::saveUser)
    }

    private fun addCreationDate(user: User) = user.apply {
        creationDate = Date()
    }

    override fun saveUser(user: User) : Single<User> {
        return userLocalDataSource.save(user).map { return@map user }
    }

    override fun findSavedUsers(limit: Int): LiveData<List<User>> {
        return userLocalDataSource.findLastUsers(limit)
    }
}