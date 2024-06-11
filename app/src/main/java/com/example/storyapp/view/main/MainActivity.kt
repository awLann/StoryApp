package com.example.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.adapter.LoadingStateAdapter
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.paging.PagingViewModel
import com.example.storyapp.paging.PagingViewModelFactory
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.add.AddStoryActivity
import com.example.storyapp.view.maps.MapsActivity
import com.example.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }
    private var token = ""
    private lateinit var story: ArrayList<ListStoryItem>
    val pagingViewModel: PagingViewModel by viewModels { PagingViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        showStoryData()
        appBar()
        addStory()
        isLogin()

        viewModel.listStory.observe(this) {
            story = it.listStory as ArrayList<ListStoryItem>
        }
    }

    private fun appBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.maps -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putParcelableArrayListExtra("location", story)
                    startActivity(intent)
                    true
                }
                R.id.logout -> {
                    viewModel.userLogout()
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun addStory() {
        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showStoryData() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val itemStory = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemStory)

        binding.rvStory.setHasFixedSize(true)

        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        pagingViewModel.pagingStory.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun isLogin() {
        showLoading(true)
        viewModel.getSession().observe(this) {
            token = it.token
            if (!it.isLogin) {
                val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                startActivity(intent)
            } else {
                showLoading(false)
                viewModel.getStory(token)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}