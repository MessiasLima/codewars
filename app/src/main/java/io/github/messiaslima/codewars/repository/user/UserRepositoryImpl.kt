package io.github.messiaslima.codewars.repository.user

import androidx.lifecycle.LiveData
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.shared.CodewarsResult
import io.github.messiaslima.codewars.repository.user.datasource.UserAPIDataSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAPIDataSouce: UserAPIDataSource
) : UserRepository {
    override fun searchUser(username: String): LiveData<CodewarsResult<User>> {
        return userAPIDataSouce.searchUser(username)
    }
}