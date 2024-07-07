package com.example.vasugram

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.vasugram.DataSource.DataModels.Story
import com.example.vasugram.adapters.UserStoryAdapter
import com.example.vasugram.ui.theme.UserStoryChangeInteraction
import timber.log.Timber

class StoryActivity : AppCompatActivity(), UserStoryChangeInteraction {

    private lateinit var userViewPager: ViewPager2
    private lateinit var userStoryAdapter: UserStoryAdapter
    private var stories: List<Story> = emptyList()
    private var currentStoryIndex : Int = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        userViewPager = findViewById(R.id.userViewPager)

        stories = intent.getParcelableArrayListExtra<Story>("stories") ?: emptyList()
        currentStoryIndex = intent.getIntExtra("currentStoryIndex",0)
        userStoryAdapter = UserStoryAdapter(stories, this)


        userViewPager.adapter = userStoryAdapter
        userViewPager.setPageTransformer(CubePageTransformer())
        userViewPager.setCurrentItem(currentStoryIndex, false)
    }

    override fun nextUser() {
        Timber.tag("vasuJain").d("nextUser is called")
        var cur = userViewPager.currentItem
        if(cur+1<stories.size){
            userViewPager.setCurrentItem(cur+1, true)
        }
        else{
            onBackPressed()
        }
    }

    override fun prevUser() {
        Timber.tag("vasuJain").d("prevUser is called")
        var cur = userViewPager.currentItem
        if(cur-1>=0){
            userViewPager.setCurrentItem(cur-1, true)
        }
        else{
            onBackPressed()
        }
    }

}

class CubePageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.cameraDistance = 20000f
        view.pivotX = if (position < 0f) view.width.toFloat() else 0f
        view.pivotY = view.height * 0.5f
        view.rotationY = 90f * position

        view.alpha = if (position <= -1f || position >= 1f) 0f else 1f
    }
}


