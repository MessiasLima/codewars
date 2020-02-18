package io.github.messiaslima.codewars.repository.challenge.datasource

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.room.paging.LimitOffsetDataSource
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.repository.common.database.CodewarsDatabase
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class ChallengeLocalDataSourceTest {

    private lateinit var challengeDAO: ChallengeLocalDataSource
    private lateinit var database: CodewarsDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, CodewarsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        challengeDAO = database.challengeLocalDataSource()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun saveAndGetChallenges() {

        // Setup
        val username = "messiaslima"
        val challengeType = ChallengeType.AUTHORED

        val challenges = listOf(Challenge(owner = username, type = challengeType))

        challengeDAO.save(challenges)

        // Test
        val dataSource = challengeDAO.find(username, challengeType)
        val result = (dataSource.create() as LimitOffsetDataSource).loadRange(0, 10)

        assertEquals(result[0]?.owner, username)
        assertEquals(result[0]?.type, challengeType)
        assertEquals(result.size, 1)
    }

    @Test
    fun saveAndGetChallenges_forList() {

        // Setup
        val username = "messiaslima"
        val challengeType = ChallengeType.AUTHORED

        val challenges = listOf(
            Challenge(id = "random", owner = username, type = challengeType),
            Challenge(id = "random2", owner = username, type = challengeType),
            Challenge(id = "random3", owner = username, type = challengeType)
        )

        challengeDAO.save(challenges)

        // Test
        val dataSource = challengeDAO.find(username, challengeType)
        val result = (dataSource.create() as LimitOffsetDataSource).loadRange(0, 10)

        assertEquals(result.size, 3)
    }
}