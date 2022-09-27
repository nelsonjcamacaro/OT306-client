package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.NewListBinding
import com.melvin.ongandroid.model.news.NewsModel

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList: List<NewsModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = newsList.size

    fun setNewsData(news: List<NewsModel>) {
        this.newsList = news
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(private val binding: NewListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsModel) {
            Glide.with(binding.root)
                .load(news.image)
                .into(binding.imgNews)
            binding.txtNewsName.text = news.name
        }
    }
}