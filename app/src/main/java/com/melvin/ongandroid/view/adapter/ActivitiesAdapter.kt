package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.NewListBinding
import com.melvin.ongandroid.model.Activity

class ActivitiesAdapter :
    ListAdapter<Activity, ActivitiesAdapter.ActivitiesViewHolder>(DiffCallBack) {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ActivitiesViewHolder {
            return ActivitiesViewHolder(
                NewListBinding.inflate(
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
            private var binding: NewListBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            /*
            Bind Activities UI Layout Item with Activity element
             */
            fun bind(activity: Activity) {
                binding.apply {
                    tvNameNews.text = activity.title ?: ""
                    tvNewDescripss.text = activity.description ?: ""
                    //TODO Uncomment and replace with this when Activity Data Class Is Complete
                    /*
                    imageNews.image?.let { imgUrl ->
                        Glide.with(imageNews.context)
                            .load(imgUrl)
                            .into(imageNews)
                    }
                     */
                    imageNews.setImageResource(R.drawable.activities_tool_img)
                }
            }

        }

        companion object {
            private val DiffCallBack = object : DiffUtil.ItemCallback<Activity>() {
                override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                    //TODO Uncomment and replace with this when Activity Data Class Is Complete
                    //return oldItem.id == newItem.id
                    return false
                }

            }
        }


}