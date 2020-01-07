package io.github.messiaslima.codewars.repository.user

import androidx.lifecycle.LiveData
import io.github.messiaslima.codewars.entity.User
import io.reactivex.Single

interface UserRepository {
    fun searchUser(username: String): Single<User>
    fun saveUser(user: User): Single<User>
    fun findSavedUsers(limit: Int = 5): LiveData<List<User>>
}