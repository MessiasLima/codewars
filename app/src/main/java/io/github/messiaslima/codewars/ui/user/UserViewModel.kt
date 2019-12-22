package io.github.messiaslima.codewars.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.messiaslima.codewars.entity.User

class UserViewModel(
    private val view: UserContract.View,
    val user: User
) : ViewModel() {

    class Factory constructor(
        private val view: UserContract.View,
        private val user: User
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(view, user) as T
        }
    }

}