package com.example.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.pref.UserModel
import com.example.storyapp.data.pref.UserPreferences
import com.example.storyapp.data.response.AddStoryResponse
import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.response.RegisterResponse
import com.example.storyapp.data.response.StoryResponse
import com.example.storyapp.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(private val preferences: UserPreferences) {

    private val _register = MutableLiveData<RegisterResponse>()
    val register: LiveData<RegisterResponse> = _register

    fun registerUser(name: String, email: String, password: String) {
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    Log.d("Register", "onResponse: ${response.message()}")
                    _register.value = response.body()
                } else {
                    Log.d("Register", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, e: Throwable) {
                Log.e("Register", "onFailure: ${e.message}")
            }
        })
    }

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login

    fun loginUser(email: String, password: String) {
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.d("Login", "onResponse: ${response.message()}")
                    _login.value = response.body()
                } else {
                    Log.d("Login", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, e: Throwable) {
                Log.e("Login", "onFailure: ${e.message}")
            }
        })
    }

    private val _listStory = MutableLiveData<StoryResponse>()
    val listStory: LiveData<StoryResponse> = _listStory

    fun getStory(token: String) {
        val client = ApiConfig.getApiService().getStories(token)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful) {
                    Log.d("Story", "onResponse: ${response.message()}")
                    _listStory.value = response.body()
                } else {
                    Log.d("Story", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, e: Throwable) {
                Log.e("Story", "onFailure: ${e.message}")
            }
        })
    }

    private val _add = MutableLiveData<AddStoryResponse>()
    val add: LiveData<AddStoryResponse> = _add

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody) {
        val client = ApiConfig.getApiService().addStories(token, file, description)
        client.enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(call: Call<AddStoryResponse>, response: Response<AddStoryResponse>) {
                if (response.isSuccessful) {
                    Log.d("AddStory", "onResponse: ${response.message()}")
                    _add.value = response.body()
                } else {
                    Log.d("AddStory", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddStoryResponse>, e: Throwable) {
                Log.e("AddStory", "onResponse: ${e.message}")
            }

        })
    }

    fun getSession(): LiveData<UserModel> {
        return preferences.getSession().asLiveData()
    }

    suspend fun saveSession(session: UserModel) {
        preferences.saveSession(session)
    }

    suspend fun userLogin() {
        preferences.login()
    }

    suspend fun userLogout() {
        preferences.logout()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreferences: UserPreferences
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreferences)
            }.also { instance == it }
    }
}