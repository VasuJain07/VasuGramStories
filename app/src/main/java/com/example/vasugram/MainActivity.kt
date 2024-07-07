package com.example.vasugram

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vasugram.adapters.StoryListAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StoryViewModel
    private lateinit var storyListAdapter: StoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val loader: ProgressBar = findViewById(R.id.loader)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel = ViewModelProvider(this).get(StoryViewModel::class.java)

        storyListAdapter = StoryListAdapter { story ->
            val stories = viewModel.stories.value ?: emptyList()
            val intent = Intent(this, StoryActivity::class.java).apply {
                putParcelableArrayListExtra("stories", ArrayList(stories))
                putExtra("currentStoryIndex", stories.indexOf(story))
            }
            startActivity(intent)
        }
        recyclerView.adapter = storyListAdapter

        viewModel.stories.observe(this, Observer { stories ->
            storyListAdapter.submitList(stories)
            loader.visibility = View.GONE
        })

        viewModel.fetchStories()
    }
}