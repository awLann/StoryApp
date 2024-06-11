package com.example.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetailStory()
    }

    private fun setDetailStory() {
        val storyData = intent.getParcelableExtra<ListStoryItem>("StoryData")
        val ivStory = storyData?.photoUrl
        val tvNameStory = storyData?.name
        val tvDescStory = storyData?.description

        Glide.with(this@DetailActivity)
            .load(ivStory)
            .into(binding.ivStory)
        binding.tvNameStory.text = tvNameStory
        binding.tvDescStory.text = tvDescStory
    }
}