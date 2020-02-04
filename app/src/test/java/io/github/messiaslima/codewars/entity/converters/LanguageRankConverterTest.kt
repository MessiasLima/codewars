package io.github.messiaslima.codewars.entity.converters

import com.google.gson.Gson
import io.github.messiaslima.codewars.entity.Color
import io.github.messiaslima.codewars.entity.Rank
import io.github.messiaslima.codewars.entity.Ranks
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class LanguageRankConverterTest {

    private val converter = LanguageRankConverter()
    private lateinit var ranks: Ranks

    @Before
    fun setup() {
        val languageRanking = HashMap<String, Rank>()
        val rank = Rank(
            rank = 1,
            name = "java",
            color = Color.BLUE,
            score = 2
        )
        languageRanking["java"] = rank
        ranks = Ranks(languages = languageRanking)
    }

    @Test
    fun languageRankToString() {
        assertEquals(converter.languageRankToString(ranks.languages), Gson().toJson(ranks))
    }

    @Test
    fun stringToLanguageRanks() {
        val ranksString = Gson().toJson(ranks)
        assertEquals(converter.stringToLanguageRanks(ranksString), ranks.languages)
    }
}