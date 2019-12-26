package io.github.messiaslima.codewars.repository.common.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.entity.converters.ColorConverter
import io.github.messiaslima.codewars.entity.converters.LanguageRankConverter
import io.github.messiaslima.codewars.entity.converters.StringListConverter
import io.github.messiaslima.codewars.repository.user.datasource.UserLocalDataSource

@Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    StringListConverter::class,
    LanguageRankConverter::class,
    ColorConverter::class
)
abstract class CodewarsDatabase : RoomDatabase() {

    abstract fun userLocalDataSource() : UserLocalDataSource

    companion object {

        lateinit var db: CodewarsDatabase
            private set

        fun initDatabase(application: Application) {
            db = Room.databaseBuilder(
                application,
                CodewarsDatabase::class.java,
                "codewars-database"
            ).build()
        }
    }
}