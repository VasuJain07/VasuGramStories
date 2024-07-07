package com.example.vasugram
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vasugram.DataSource.DataModels.Story
import com.example.vasugram.DataSource.StoryRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.Forest.plant


class StoryViewModel : ViewModel() {

    private val repository = StoryRepository()

    private val _stories = MutableLiveData<List<Story>>()
    val stories: LiveData<List<Story>> get() = _stories

    init {
        plant(Timber.DebugTree())
    }

    fun fetchStories() {
        viewModelScope.launch {
            try {
                repository.getStories { storiesList ->
                    _stories.postValue(storiesList)
                }
            }
            catch (e: Exception){
                fetchStoriesMock(100)
            }
        }
    }
    private fun fetchStoriesMock(num: Int) {
        var storyList = emptyList<Story>()
        var i=1
        var k=0
        while(k<=num){
            k+=1
            var toadd = Story(
                userId = "user${k}",
                timestamp = 100,
                userAvatarUrl = "https://picsum.photos/id/${i++}/300/300",
                imageUrls = ArrayList( listOf(
                    "https://picsum.photos/id/${i++}/300/300",
                    "https://picsum.photos/id/${i++}/300/300"
                )
                )
            )
            storyList= storyList + toadd

        }
        _stories.value = storyList
    }
}