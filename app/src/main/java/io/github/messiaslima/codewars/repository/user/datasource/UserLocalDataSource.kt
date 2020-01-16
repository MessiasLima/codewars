package io.github.messiaslima.codewars.repository.user.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.messiaslima.codewars.entity.User
import io.reactivex.Single

@Dao
interface UserLocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User): Long

    @Query("SELECT * FROM User ORDER BY creationDate desc LIMIT :limit;")
    fun findLastUsers(limit: Int): LiveData<List<User>>
}