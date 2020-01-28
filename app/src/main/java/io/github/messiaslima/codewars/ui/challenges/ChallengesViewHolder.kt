package io.github.messiaslima.codewars.ui.challenges

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.messiaslima.codewars.entity.Challenge
import kotlinx.android.synthetic.main.list_item_challenge.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ChallengesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val challengeName = itemView.listItemChallengeName
    private val date = itemView.listItemChallengeDate

    fun bind(challenge: Challenge?) {
        challengeName.text = challenge?.name
        date.text = getFormattedDate(challenge?.completedAt ?: challenge?.publishedAt)
    }

    private fun getFormattedDate(date: Date?): String {

        if (date == null) return ""

        try {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            return simpleDateFormat.format(date)
        } catch (ex: ParseException) {
            return ""
        }
    }
}