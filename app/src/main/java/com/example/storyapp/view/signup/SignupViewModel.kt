package com.example.storyapp.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.Repository
import com.example.storyapp.data.response.RegisterResponse
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: Repository): ViewModel() {
    val register: LiveData<RegisterResponse> = repository.register

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.registerUser(name, email, password)
        }
    }
}