package io.github.messiaslima.codewars.entity.converters

import androidx.room.TypeConverter
import io.github.messiaslima.codewars.ui.challenges.ChallengeType

class ChallengeTypeConverter {
    @TypeConverter
    fun challengeTypeToString(challengeType: ChallengeType) = challengeType.name
    @TypeConverter
    fun stringToChallengeType(string: String) = ChallengeType.valueOf(string)
}