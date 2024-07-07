package com.example.vasugram.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.vasugram.DataSource.DataModels.Story
import com.example.vasugram.R
import com.example.vasugram.UserStoryView
import com.example.vasugram.ui.theme.UserStoryChangeInteraction

class UserStoryAdapter(private val stories: List<Story>, private val parentStoryActivity: UserStoryChangeInteraction) : RecyclerView.Adapter<UserStoryAdapter.UserStoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserStoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_story_item, parent, false)
        return UserStoryViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: UserStoryViewHolder, position: Int) {
        val story = stories[position]
        holder.bind(story, parentStoryActivity)
    }

    override fun getItemCount(): Int = stories.size

    class UserStoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userStoryView: UserStoryView = itemView.findViewById(R.id.userStoryView)

        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(story: Story, p:UserStoryChangeInteraction) {
            userStoryView.setImageUrls(story.imageUrls!!, story.userId!!)
            userStoryView.setViewPagerInteraction(p)
        }
    }
}