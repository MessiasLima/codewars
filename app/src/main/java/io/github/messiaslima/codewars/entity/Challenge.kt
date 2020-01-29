package io.github.messiaslima.codewars.entity

import androidx.annotation.NonNull
import androidx.room.*
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import java.io.Serializable
import java.util.*


@Entity
data class Challenge (

    @NonNull
    @PrimaryKey
    var id: String = "",
    var name: String? = null,
    var slug: String? = null,
    var category: String? = null,
    var publishedAt: Date? = null,
    var approvedAt: Date? = null,
    var completedAt: Date? = null,
    var languages: List<String>? = null,
    var url: String? = null,
    var createdAt: String? = null,
    var owner: String? = null,
    @Embedded(prefix = "createdBy_")
    var createdBy: User? = null,

    @Embedded(prefix = "approvedBy_")
    var approvedBy: User? = null,

    var description: String? = null,
    var totalAttempts: Long? = null,
    var totalCompleted: Long? = null,
    var totalStars: Long? = null,
    var voteScore: Long? = null,
    var tags: List<String>? = null,
    var contributorsWanted: Boolean? = null,
    var type: ChallengeType? = null
): Serializable