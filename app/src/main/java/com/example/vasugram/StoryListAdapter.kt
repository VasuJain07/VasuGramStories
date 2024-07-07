package com.example.vasugram

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vasugram.DataModels.Story
import de.hdodenhof.circleimageview.CircleImageView

class StoryListAdapter(private val onClick: (Story) -> Unit) : ListAdapter<Story, StoryListAdapter.StoryViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_thumbnail_item, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story, onClick)
    }

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: CircleImageView = itemView.findViewById(R.id.storyThumbnailImageView)
        private val usernameTextView: TextView = itemView.findViewById(R.id.storyUsernameTextView)
        private val timeStampView: TextView = itemView.findViewById(R.id.storyTimeStampTextView)
        @SuppressLint("SetTextI18n")
        fun bind(story: Story, onClick: (Story) -> Unit) {
            Glide.with(itemView.context).load(story.userAvatarUrl).into(imageView)
            usernameTextView.text = story.userId
            timeStampView.text = "${story.timestamp} ms"
            itemView.setOnClickListener {
                onClick(story)
            }
        }
    }
}