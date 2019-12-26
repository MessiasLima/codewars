package io.github.messiaslima.codewars.entity

import java.io.Serializable
import java.util.*

data class Challenge (
    var id: String? = null,
    var name: String? = null,
    var slug: String? = null,
    var category: String? = null,
    var publishedAt: Date? = null,
    var approvedAt: Date? = null,
    var completedAt: Date? = null,
    var languages: List<String>? = null,
    var url: String? = null,
    var createdAt: String? = null,
    var createdBy: User? = null,
    var approvedBy: User? = null,
    var description: String? = null,
    var totalAttempts: Long? = null,
    var totalCompleted: Long? = null,
    var totalStars: Long? = null,
    var voteScore: Long? = null,
    var tags: List<String>? = null,
    var contributorsWanted: Boolean? = null
): Serializable