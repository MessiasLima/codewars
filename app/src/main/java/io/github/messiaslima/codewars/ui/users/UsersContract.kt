package io.github.messiaslima.codewars.ui.users

import io.github.messiaslima.codewars.entity.User

interface UsersContract {
    interface View {
        fun navigateToDetails(user: User)
        fun handleError(message: String, throwable: Throwable?)
    }
}