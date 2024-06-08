package com.example.storyapp.di

import android.content.Context
import com.example.storyapp.data.Repository
import com.example.storyapp.data.pref.UserPreferences
import com.example.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        return Repository.getInstance(preferences)
    }
}