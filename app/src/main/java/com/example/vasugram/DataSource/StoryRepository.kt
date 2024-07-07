package com.example.vasugram.DataSource

import com.example.vasugram.DataSource.DataModels.Story
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class StoryRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getStories(callback: (List<Story>) -> Unit) {
        firestore.collection("stories").get()
            .addOnSuccessListener { result ->
                val stories = result.map { document ->
                    document.toObject(Story::class.java)
                }
                Timber.tag("vasuJain").d("on success stories: $stories")
                callback(stories)
            }.addOnFailureListener{ e ->
                Timber.tag("vasuJain").d("on failer stories: $e")
                throw e
            }
    }
}