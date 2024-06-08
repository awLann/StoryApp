package com.example.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
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
        val ivStory = intent.extras!!.getString(EXTRA_STORY_IMAGE)
        val tvNameStory = intent.extras!!.getString(EXTRA_STORY_NAME)
        val tvDescStory = intent.extras!!.getString(EXTRA_STORY_DESC)

        Glide.with(this@DetailActivity)
            .load(ivStory)
            .into(binding.ivStory)
        binding.tvNameStory.text = tvNameStory
        binding.tvDescStory.text = tvDescStory
    }

    companion object {
        const val EXTRA_STORY_IMAGE = "extra_story_image"
        const val EXTRA_STORY_NAME = "extra_story_name"
        const val EXTRA_STORY_DESC = "extra_story_desc"
    }
}