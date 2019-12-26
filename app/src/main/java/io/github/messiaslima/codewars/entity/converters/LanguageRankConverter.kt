package io.github.messiaslima.codewars.entity.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import io.github.messiaslima.codewars.entity.Overall
import io.github.messiaslima.codewars.entity.Ranks


class LanguageRankConverter {

    @TypeConverter
    fun languageRankToString(languages: Map<String, Overall>?): String {
        return Gson().toJson(Ranks(languages = languages))
    }

    @TypeConverter
    fun stringToLanguageRanks(string: String): Map<String, Overall>? {
        return Gson().fromJson(string, Ranks::class.java).languages
    }
}