package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.MembersCardViewBinding
import com.melvin.ongandroid.model.NosotrosActivities.MembersList

class MembersAdapter(var membersList:List<MembersList>):RecyclerView.Adapter<MembersAdapter.MembersViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val layoutInflater =LayoutInflater.from(parent.context)
        val binding = MembersCardViewBinding.inflate(layoutInflater,parent,false)
        return MembersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        holder.bind(membersList[position])
    }

    override fun getItemCount(): Int {
        return membersList.size
    }

    inner class MembersViewHolder(private val binding: MembersCardViewBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(members: MembersList){
        val url = members.image
            binding.nameMemberTV.text = members.name
            binding.rolTV.text = members.description
        val into = Glide.with(binding.membersPhoto.context).load(url).into(binding.membersPhoto)
        }
    }
}