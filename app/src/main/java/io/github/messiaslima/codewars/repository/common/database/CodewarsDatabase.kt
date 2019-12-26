package io.github.messiaslima.codewars.repository.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.entity.converters.ColorConverter
import io.github.messiaslima.codewars.entity.converters.LanguageRankConverter
import io.github.messiaslima.codewars.entity.converters.StringListConverter

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
abstract class CodewarsDatabase : RoomDatabase()