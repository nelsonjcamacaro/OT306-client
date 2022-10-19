package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.NovedadesItemBinding
import com.melvin.ongandroid.model.inicioActivitys.Activity
import com.melvin.ongandroid.utils.Extensions.formatDescription

class ActivitiesAdapter :
    ListAdapter<Activity, ActivitiesAdapter.ActivitiesViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivitiesViewHolder {
        return ActivitiesViewHolder(
            NovedadesItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ActivitiesViewHolder(
        private var binding: NovedadesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /*
         * Bind Activities UI Layout Item with Activity element
         */
        fun bind(activity: Activity) {
            binding.apply {
                tvNameNews.text = activity.name ?: ""
                tvDescriptionNews.text = activity.description.formatDescription()
                activity.image?.let { imgUrl ->
                    Glide.with(root.context)
                        .load(imgUrl)
                        .centerCrop()
                        .into(iVNovedades)
                }
            }
        }

    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Activity>() {
            override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }


}