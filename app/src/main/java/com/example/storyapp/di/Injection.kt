package com.example.storyapp.di

import android.content.Context
import com.example.storyapp.data.Repository
import com.example.storyapp.data.pref.UserPreferences
import com.example.storyapp.data.pref.dataStore
import com.example.storyapp.data.retrofit.ApiConfig
import com.example.storyapp.data.database.StoryDatabase
import com.example.storyapp.paging.PagingRepository

object Injection {
    fun provideRepository(context: Context): Repository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        return Repository.getInstance(preferences)
    }

    fun providePaging(context: Context): PagingRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        val preferences = UserPreferences.getInstance(context.dataStore)
        return PagingRepository(database, apiService, preferences)
    }
}