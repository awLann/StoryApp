package com.example.storyapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.data.response.ListStoryItem
import com.example.storyapp.databinding.ItemRowStoryBinding
import com.example.storyapp.view.detail.DetailActivity

class StoryAdapter(private val listStory: List<ListStoryItem>): RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemRowStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(storyData: ListStoryItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(storyData.photoUrl)
                    .into(binding.ivStory)
                binding.tvNameStory.text = storyData.name
                binding.tvDescStory.text = storyData.description
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_STORY_IMAGE, storyData.photoUrl)
                intent.putExtra(DetailActivity.EXTRA_STORY_NAME, storyData.name)
                intent.putExtra(DetailActivity.EXTRA_STORY_DESC, storyData.description)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position])
    }
}