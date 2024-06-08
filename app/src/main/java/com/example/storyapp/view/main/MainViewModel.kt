package com.example.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.Repository
import com.example.storyapp.data.pref.UserModel
import com.example.storyapp.data.response.StoryResponse
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    val listStory: LiveData<StoryResponse> = repository.listStory

    fun getStory(token: String) {
        viewModelScope.launch {
            repository.getStory(token)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }

    fun userLogout() {
        viewModelScope.launch {
            repository.userLogout()
        }
    }
}