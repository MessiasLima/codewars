package io.github.messiaslima.codewars.ui.users

import dagger.Component
import io.github.messiaslima.codewars.repository.user.UserRepositoryModule

@Component(modules = [UserRepositoryModule::class])
interface UsersComponent {
    fun inject(usersFragment: UsersFragment)
}