package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.ImageListBinding
import com.melvin.ongandroid.model.inicioActivitys.Activity
import com.melvin.ongandroid.utils.Extensions.formatDescription

class HorizontalAdapter(private var activitiesList: List<Activity>): RecyclerView.Adapter<HorizontalAdapter.ActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageListBinding.inflate(layoutInflater, parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(activitiesList[position])

    }

    override fun getItemCount(): Int {
        return activitiesList.size
    }

    inner class ActivityViewHolder(private val binding: ImageListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(activity: Activity){
            binding.apply {
                tvWelcome.text = activity.name ?: ""
                tvWelcomeDescrip.text = activity.description.formatDescription()
                activity.image?.let { urlImage->
                    Glide
                        .with(root)
                        .load(urlImage)
                        .centerCrop()
                        .into(imageView)
                }
            }

        }

    }
}