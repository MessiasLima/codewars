package io.github.messiaslima.codewars.repository.challenge.datasource

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.messiaslima.codewars.entity.Challenge

@Dao
interface ChallengeLocalDataSource {

    @Query("SELECT * FROM Challenge")
    fun findAll(): DataSource.Factory<Int, Challenge>

    @Insert
    fun save(vararg challenge: Challenge): List<Long>
}