package io.github.messiaslima.codewars.repository.common.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.entity.converters.*
import io.github.messiaslima.codewars.repository.challenge.datasource.ChallengeLocalDataSource
import io.github.messiaslima.codewars.repository.user.datasource.UserLocalDataSource

@Database(
    entities = [
        User::class,
        Challenge::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    StringListConverter::class,
    LanguageRankConverter::class,
    ColorConverter::class,
    DateConverter::class,
    ChallengeTypeConverter::class
)
abstract class CodewarsDatabase : RoomDatabase() {

    abstract fun userLocalDataSource() : UserLocalDataSource
    abstract fun challengeLocalDataSource(): ChallengeLocalDataSource

    companion object {

        lateinit var db: CodewarsDatabase
            private set

        private val migration1to2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE User ADD COLUMN creationDate INTEGER")
            }
        }

        fun initDatabase(application: Application) {
            db = Room.databaseBuilder(
                application,
                CodewarsDatabase::class.java,
                "codewars-database"
            )
                .addMigrations(
                    migration1to2
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}