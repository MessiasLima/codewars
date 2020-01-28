package io.github.messiaslima.codewars.repository.challenge

import dagger.Module
import dagger.Provides
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengeAPIDataSource
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengeLocalDataSource
import io.github.messiaslima.codewars.repository.common.api.CodewarsServiceModule
import io.github.messiaslima.codewars.repository.common.database.CodewarsDatabaseModule

@Module(includes = [
    CodewarsServiceModule::class,
    CodewarsDatabaseModule::class
])
class ChallengeRepsitoryModule {

    @Provides
    fun provideChallengeRepository(
        challengeAPIDataSource: ChallengeAPIDataSource,
        challengeLocalDataSource: ChallengeLocalDataSource
    ): ChallengeRepository{
        return ChallengeRepositoryImpl(challengeAPIDataSource, challengeLocalDataSource)
    }

}