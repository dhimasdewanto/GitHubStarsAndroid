package com.dhimasdewanto.githubstars.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import kotlinx.android.synthetic.main.item_github_stars.view.*

class GitHubStarsRecyclerAdapter(private val interaction: GitHubStarsViewHolder.Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GitHubStars>() {

        override fun areItemsTheSame(oldItem: GitHubStars, newItem: GitHubStars): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GitHubStars, newItem: GitHubStars): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return GitHubStarsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_github_stars,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GitHubStarsViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<GitHubStars>) {
        differ.submitList(list)
    }

    class GitHubStarsViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.image_avatar
        private val title = itemView.textview_title
        private val subTitle = itemView.textview_subtitle
        private val stars = itemView.textview_stars

        fun bind(item: GitHubStars) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            title.text = "${item.ownerName}/${item.name}"
            subTitle.text = item.description
            stars.text = "${item.starsCount}"
            setImage(item)
        }

        private fun setImage(gitHubStars: GitHubStars) {
            Glide.with(itemView.context)
                .load(gitHubStars.ownerImageUrl)
                .into(image)
        }

        interface Interaction {
            fun onItemSelected(position: Int, item: GitHubStars)
        }
    }
}