package io.github.messiaslima.codewars.repository.challenge.datasource

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.ui.challenges.ChallengeType

@Dao
interface ChallengeLocalDataSource {

    @Query("SELECT * FROM Challenge WHERE owner == :username AND type = :challengeType")
    fun find(
        username: String,
        challengeType: ChallengeType
    ): DataSource.Factory<Int, Challenge>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(challenge: List<Challenge>): List<Long>
}