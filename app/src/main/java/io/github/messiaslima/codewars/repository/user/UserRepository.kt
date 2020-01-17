package io.github.messiaslima.codewars.repository.user

import androidx.lifecycle.LiveData
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.common.api.ApiResponse
import io.github.messiaslima.codewars.repository.common.api.ApiSuccessResponse
import io.github.messiaslima.codewars.util.Resource
import io.reactivex.Single

interface UserRepository {

    fun searchUserV2(username: String): LiveData<Resource<User>>

    fun findSavedUsers(
        sortByHonor: Boolean = false,
        limit: Int = 5
    ): LiveData<Resource<List<User>>>
}