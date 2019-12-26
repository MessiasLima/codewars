package io.github.messiaslima.codewars.repository.common.database

import dagger.Module
import dagger.Provides
import io.github.messiaslima.codewars.repository.user.datasource.UserLocalDataSource

@Module
class CodewarsDatabaseModule {

    @Provides
    fun provideDatabase(): CodewarsDatabase{
        return CodewarsDatabase.db
    }

    @Provides
    fun provideUserDAO(database: CodewarsDatabase): UserLocalDataSource {
        return database.userLocalDataSource()
    }
}