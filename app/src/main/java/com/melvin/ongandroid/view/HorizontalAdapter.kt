package com.melvin.ongandroid.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.Activity

class HorizontalAdapter( private val activitiesList: List<Activity>): RecyclerView.Adapter<HorizontalAdapter.Activityviewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Activityviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_list, parent, false)
        return Activityviewholder(view)
    }

    override fun onBindViewHolder(holder: Activityviewholder, position: Int) {

    }

    override fun getItemCount(): Int {
        return activitiesList.size
    }

    inner class Activityviewholder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}