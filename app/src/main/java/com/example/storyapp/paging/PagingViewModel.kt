package com.example.storyapp.paging

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.di.Injection

class PagingViewModel(storyRepository: PagingRepository): ViewModel() {

    val pagingStory: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStoryPaging().cachedIn(viewModelScope)
}

class PagingViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PagingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PagingViewModel(Injection.providePaging(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}