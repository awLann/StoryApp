package com.example.storyapp.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.Repository
import com.example.storyapp.data.pref.UserModel
import com.example.storyapp.data.response.AddStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: Repository): ViewModel() {
    val add: LiveData<AddStoryResponse> = repository.add

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody, lat: RequestBody?, lon: RequestBody?) {
        viewModelScope.launch {
            repository.addStory(token, file, description, lat, lon)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }
}