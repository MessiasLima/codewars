package io.github.messiaslima.codewars.ui.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.entity.Challenge
import kotlinx.android.synthetic.main.list_item_challenge.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ChallengesAdapter(
    private val onChallengeClickedCallback: (challenge: Challenge) -> Unit
) : PagedListAdapter<Challenge, ChallengesViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_challenge,
            parent,
            false
        )

        return ChallengesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengesViewHolder, position: Int) {
        getItem(position)?.let { challenge ->
            holder.bind(challenge)
            holder.itemView.setOnClickListener {
                onChallengeClickedCallback.invoke(challenge)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Challenge>() {

            override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
                return oldItem == newItem
            }

        }
    }
}