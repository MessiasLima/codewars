package io.github.messiaslima.codewars.repository.user

import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.user.datasource.UserAPIDataSource
import io.github.messiaslima.codewars.repository.user.datasource.UserLocalDataSource
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAPIDataSouce: UserAPIDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override fun searchUser(username: String): Single<User> {
        return userAPIDataSouce.searchUser(username)
            .flatMap(this::saveUser)
    }

    override fun saveUser(user: User) : Single<User> {
        return userLocalDataSource.save(user).map { return@map user }
    }
}