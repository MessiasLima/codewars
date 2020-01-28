package io.github.messiaslima.codewars.repository.user

import dagger.Module
import dagger.Provides
import io.github.messiaslima.codewars.repository.common.api.CodewarsServiceModule
import io.github.messiaslima.codewars.repository.common.database.CodewarsDatabaseModule
import io.github.messiaslima.codewars.repository.user.datasource.UserAPIDataSource
import io.github.messiaslima.codewars.repository.user.datasource.UserLocalDataSource

@Module(includes = [
    CodewarsServiceModule::class,
    CodewarsDatabaseModule::class
])
class UserRepositoryModule {

    @Provides
    fun provideUserRepository(
        userAPI: UserAPIDataSource,
        userDAO: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(userAPI, userDAO)
    }
}