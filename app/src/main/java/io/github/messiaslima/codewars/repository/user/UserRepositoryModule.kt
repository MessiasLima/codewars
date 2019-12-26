package io.github.messiaslima.codewars.repository.user

import dagger.Module
import dagger.Provides
import io.github.messiaslima.codewars.repository.common.api.CodewarsServiceModule
import io.github.messiaslima.codewars.repository.user.datasource.UserAPIDataSource

@Module(includes = [CodewarsServiceModule::class])
class UserRepositoryModule {

    @Provides
    fun provideUserRepository(userAPIDataSource: UserAPIDataSource): UserRepository {
        return UserRepositoryImpl(userAPIDataSource)
    }
}