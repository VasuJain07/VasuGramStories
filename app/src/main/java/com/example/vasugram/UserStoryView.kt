package com.example.vasugram

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.vasugram.ui.theme.UserStoryChangeInteraction

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("ClickableViewAccessibility")
class UserStoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {
    private var grandParentViewPager: UserStoryChangeInteraction? = null
    private val imageView: ImageView
    private val progressBar: ProgressBar
    private val userId: TextView
    private var imageUrls: List<String> = emptyList()
    private var currentImageIndex = 0
    private val gestureDetector: GestureDetector

    private val handler = Handler(Looper.getMainLooper())
    private var autoScrollRunnable : Runnable? = null
    private var autoScrollDelay: Long = 5000 // 5 seconds

    init {
        LayoutInflater.from(context).inflate(R.layout.story_item, this, true)
        imageView = findViewById(R.id.storyImageView)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE
        userId = findViewById(R.id.userIdTextView)

        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val width = width
                if (e.x < width / 2) {
                    showPreviousImage()
                } else {
                    showNextImage()
                }
                return true
            }
        })

        setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
    }

    fun setViewPagerInteraction(p:UserStoryChangeInteraction){
        grandParentViewPager = p
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun setImageUrls(imageUrls: ArrayList<String>, userName: String) {
        this.imageUrls = imageUrls
        currentImageIndex = 0
        userId.text = userName
        showImage(currentImageIndex)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showImage(index: Int) {
        if (index in imageUrls.indices) {
//            autoScrollRunnable?.let { handler.removeCallbacks(it) }
            Glide.with(context).load(imageUrls[index]).into(imageView)
//            autoScrollRunnable = Runnable { showNextImage() }
//            autoScrollRunnable?.let{handler.postDelayed(it,autoScrollDelay)}
        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showPreviousImage() {
        if (currentImageIndex > 0) {
            currentImageIndex -= 1
            showImage(currentImageIndex)
        }
        else{
            grandParentViewPager?.prevUser()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showNextImage() {
        if (currentImageIndex < imageUrls.size - 1) {
            currentImageIndex += 1
            showImage(currentImageIndex)
        } else {
            grandParentViewPager?.nextUser()
        }
    }

}

