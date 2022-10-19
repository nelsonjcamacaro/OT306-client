package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.MembersCardViewBinding
import com.melvin.ongandroid.model.nosotrosActivities.model.MemberDto
import com.melvin.ongandroid.model.nosotrosActivities.model.getFormattedDescription

class MembersAdapter(
    private var members: List<MemberDto>,
    private val onClickMember: OnMembersClicked
) :
    RecyclerView.Adapter<MembersAdapter.MembersViewHolder>() {

    interface OnMembersClicked {
        fun onMemberClickListener(member: MemberDto, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MembersCardViewBinding.inflate(layoutInflater, parent, false)
        return MembersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        val member = members[position]
        holder.bind(member)
        holder.itemView.setOnClickListener {
            onClickMember.onMemberClickListener(members[position],position)
        }
    }

    override fun getItemCount(): Int {
        return members.size
    }

    /*
     * Bind every member item to its own Member Card View
     */
    inner class MembersViewHolder(private val binding: MembersCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(member: MemberDto) {
            binding.nameMemberTV.text = member.name ?: ""
            binding.rolTV.text = member.getFormattedDescription()
            member.image?.let { url ->
                Glide.with(binding.membersPhoto.context).load(url).into(binding.membersPhoto)
            }
        }
    }
}