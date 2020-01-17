package io.github.messiaslima.codewars.repository.user.datasource

import io.github.messiaslima.codewars.repository.common.api.CodewarsService
import javax.inject.Inject

class UserAPIDataSource @Inject constructor(
    private val codewarsService: CodewarsService
) {
    fun searchUser(username: String) = codewarsService.searchUser(username)
}