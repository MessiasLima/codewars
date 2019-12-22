package io.github.messiaslima.codewars.repository.user.datasource

import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.shared.CodewarsService
import io.reactivex.Single
import javax.inject.Inject

class UserAPIDataSource @Inject constructor(
    private val codewarsService: CodewarsService
) {

    fun searchUser(username: String): Single<User> {
        return codewarsService.searchUser(username)
    }

}