package io.github.messiaslima.codewars.repository.user.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.messiaslima.codewars.entity.User
import io.reactivex.Single

@Dao
interface UserLocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User): Single<Long>

    @Query("SELECT * FROM User LIMIT :limit")
    fun findAll(limit: Int): Single<List<User>>
}