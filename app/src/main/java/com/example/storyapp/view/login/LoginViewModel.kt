package com.example.storyapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.Repository
import com.example.storyapp.data.pref.UserModel
import com.example.storyapp.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {
    val login: LiveData<LoginResponse> = repository.login

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(email, password)
        }
    }

    fun saveSession(session: UserModel) {
        viewModelScope.launch {
            repository.saveSession(session)
        }
    }

    fun userLogin() {
        viewModelScope.launch {
            repository.userLogin()
        }
    }
}