package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.BuildConfig
import com.melvin.ongandroid.databinding.ImageListBinding
import com.melvin.ongandroid.model.InicioActivitys.Activity

class HorizontalAdapter(var activitiesList: List<Activity>): RecyclerView.Adapter<HorizontalAdapter.Activityviewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Activityviewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageListBinding.inflate(layoutInflater, parent, false)
        return Activityviewholder(binding)
    }

    override fun onBindViewHolder(holder: Activityviewholder, position: Int) {
        holder.bind(activitiesList[position])

    }

    override fun getItemCount(): Int {
        return activitiesList.size
    }

    inner class Activityviewholder(private val binding: ImageListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(activity: Activity){
            binding.tvWelcome.text = activity.name
            binding.tvWelcomeDescrip.text = activity.description

            Glide
                .with(binding.root.context)
                .load("${BuildConfig.API_URL}${activity.image}")
                .into(binding.imageView)


        }

    }
}