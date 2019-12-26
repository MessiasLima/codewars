package io.github.messiaslima.codewars.ui.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.entity.Challenge
import kotlinx.android.synthetic.main.list_item_challenge.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ChallengesAdapter(
    private val onChallengeClickedCallback: (challenge: Challenge) -> Unit
) : RecyclerView.Adapter<ChallengesAdapter.ChallengesViewHolder>() {

    private val challenges = mutableListOf<Challenge>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_challenge,
            parent,
            false
        )

        return ChallengesViewHolder(view)
    }

    override fun getItemCount() = challenges.size

    override fun onBindViewHolder(holder: ChallengesViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.bind(challenge)
        holder.itemView.setOnClickListener {
            onChallengeClickedCallback.invoke(challenge)
        }
    }

    fun addChallenges(newChallenges: List<Challenge>){
        challenges.addAll(newChallenges)
        notifyDataSetChanged()
    }

    class ChallengesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val challengeName = itemView.listItemChallengeName
        private val date = itemView.listItemChallengeDate

        fun bind(challenge: Challenge) {
            challengeName.text = challenge.name
            date.text = getFormattedDate(challenge.completedAt)
        }

        private fun getFormattedDate(completedAt: Date?): String {

            if (completedAt == null) return ""

            try {
                val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                return simpleDateFormat.format(completedAt)
            } catch (ex: ParseException) {
                return ""
            }
        }
    }
}