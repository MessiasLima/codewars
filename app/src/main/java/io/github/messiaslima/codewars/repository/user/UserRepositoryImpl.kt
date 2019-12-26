package io.github.messiaslima.codewars.repository.user

import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.user.datasource.UserAPIDataSource
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAPIDataSouce: UserAPIDataSource
) : UserRepository {

    override fun searchUser(username: String): Single<User> {
        return userAPIDataSouce.searchUser(username)
    }
}