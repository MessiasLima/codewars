package io.github.messiaslima.codewars.repository.user

import androidx.lifecycle.LiveData
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.shared.CodewarsResult

interface UserRepository {
    fun searchUser(username: String): LiveData<CodewarsResult<User>>
}