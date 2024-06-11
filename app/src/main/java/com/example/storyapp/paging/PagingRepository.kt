package com.example.storyapp.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.data.database.StoryDatabase
import com.example.storyapp.data.database.StoryPagingSource
import com.example.storyapp.data.pref.UserPreferences
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.data.retrofit.ApiService

class PagingRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService, private val preferences: UserPreferences) {
    fun getStoryPaging(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, preferences)
            }
        ).liveData
    }
}