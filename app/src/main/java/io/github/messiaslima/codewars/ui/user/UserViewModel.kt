package io.github.messiaslima.codewars.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.messiaslima.codewars.entity.User

class UserViewModel(
    user: User
) : ViewModel() {

    var name = user.name ?: user.username
    var username = if (user.name.isNullOrEmpty()) null else user.username

    class Factory constructor(
        private val user: User
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(user) as T
        }
    }

}