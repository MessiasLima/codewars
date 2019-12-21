package io.github.messiaslima.codewars.ui.shared

import dagger.Component
import io.github.messiaslima.codewars.ui.users.UsersViewModel

@Component
interface ViewModelInjectorComponent {
    fun inject(usersViewModel: UsersViewModel)
}