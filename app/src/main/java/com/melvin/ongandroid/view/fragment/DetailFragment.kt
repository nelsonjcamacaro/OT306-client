package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentDetailBinding
import com.melvin.ongandroid.model.nosotrosActivities.model.MemberDto
import com.melvin.ongandroid.model.nosotrosActivities.model.getFormattedDescription
import com.melvin.ongandroid.viewmodel.MembersViewModel
import com.melvin.ongandroid.viewmodel.MembersViewModelFactory

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val membersViewModel: MembersViewModel by viewModels(
        factoryProducer = { MembersViewModelFactory() })

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        setupToolBarNavigation()

        bindMemberDetail(args.member)
    }

    // toolBar backstack navigation
    private fun setupToolBarNavigation() {
        binding.toolbarDetail.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToMembersFragment()
            findNavController().navigate(action)
        }
    }

    private fun bindMemberDetail(member: MemberDto) {
        binding.apply {
            tvNameDetail.text = member.name
            tvDrescripDetail.text = member.getFormattedDescription()
            tvFaceDetail.text = member.facebookUrl
            tvLinkedinDetails.text = member.linkedinUrl
            Glide.with(root).load(member.image).into(ivDetail)
        }
    }
}