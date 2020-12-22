package com.techday2020.ui.main.view.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.techday2020.R
import com.techday2020.databinding.ItemMatchBinding
import com.techday2020.ui.model.Match
import java.util.*


class MatchRecyclerAdapter(
    private var matches: List<Match>
) : RecyclerView.Adapter<MatchRecyclerAdapter.Holder>() {

    var selectedPosition = 0

    lateinit var onItemClickListener: (match: Match) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )
    }

    override fun getItemCount(): Int {
        return matches.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(matches[position], position == selectedPosition, position)
    }

    fun replaceMatches(matches: List<Match>) {
        this.matches = matches
        notifyDataSetChanged()
    }

    inner class Holder(
        private val binding: ItemMatchBinding,
        private val onItemClickListener: (match: Match) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match, selected: Boolean, pos: Int) {
            with(binding.root.context) {
                binding.matchRootLinearLayout.background =
                    if (selected) {
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.dr_selected_match_card
                        )
                    } else {
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.dr_card_match_gradient
                        )
                    }


                binding.homeTeamTextView.text = match.homeTeam.toUpperCase(Locale.getDefault())
                binding.homeScoreTextView.text = match.homeScore.toString()
                binding.homeTeamImageView.apply {
                    val inputStream = assets.open("logos/"+match.homeDrawable)
                    val d = Drawable.createFromStream(inputStream, null)
                    setImageDrawable(d)
                }

                binding.visitorTeamTextView.text = match.visitorTeam.toUpperCase(Locale.getDefault())
                binding.visitorScoreTextView.text = match.visitorScore.toString()
                binding.visitorTeamImageView.apply {
                    val inputStream = assets.open("logos/"+match.visitorDrawable)
                    val d = Drawable.createFromStream(inputStream, null)
                    setImageDrawable(d)
                }

                binding.root.setOnClickListener {
                    if (selectedPosition == pos)
                        return@setOnClickListener

                    selectedPosition = pos
                    onItemClickListener.invoke(match)
                    notifyDataSetChanged()
                }
            }
        }
    }
}
