package com.melvin.ongandroid.view.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.NovedadesItemBinding
import com.melvin.ongandroid.model.news.NewsModel

class NovedadesAdapter(var news: List<NewsModel>) :
    RecyclerView.Adapter<NovedadesAdapter.NovedadesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovedadesViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NovedadesItemBinding.inflate(layoutInflater, parent, false)
        return NovedadesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NovedadesViewHolder, position: Int) {
        news[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    inner class NovedadesViewHolder(private val binding: NovedadesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: NewsModel) {
            binding.tvNameNews.text = news.name
            binding.tvDescripNews.text = Html.fromHtml(news.content ?: "")
            news.image.let { urlImage ->
                Glide.with(binding.root.context).load(urlImage).into(binding.iVNovedades)
            }
        }
    }

}