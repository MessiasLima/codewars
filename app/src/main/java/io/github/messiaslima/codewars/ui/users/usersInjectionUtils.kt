package io.github.messiaslima.codewars.ui.users

import dagger.Component
import dagger.Module
import dagger.Provides
import io.github.messiaslima.codewars.repository.user.UserRepository
import io.github.messiaslima.codewars.repository.user.UserRepositoryImpl

@Component(modules = [UsersModule::class])
interface UsersComponent {
    fun inject(usersFragment: UsersFragment)
}

@Module
class UsersModule {
    @Provides
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()
}