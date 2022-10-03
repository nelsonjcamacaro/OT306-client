package com.melvin.ongandroid.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentDetailBinding
import com.melvin.ongandroid.model.nosotrosActivities.model.MemberDto
import com.melvin.ongandroid.model.nosotrosActivities.model.getFormattedDescription

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        setupToolBarNavigation()
        goToSocialMediaWebPages()
        bindMemberDetail(args.member)
    }

    // toolBar backstack navigation
    private fun setupToolBarNavigation() {
        binding.toolbarDetail.setOnClickListener {
            val action =  DetailFragmentDirections.actionDetailFragmentToMembersFragment()
            findNavController().navigate(action)
        }
    }

    //envia al usuario a una vista de navegador de las redes sociales de los miembros
    private fun goToSocialMediaWebPages(){
        val facebookURL = binding.tvFaceDetail.text.toString()
        val linkedInURL = binding.tvLinkedinDetails.text.toString()
        val intent = Intent(Intent.ACTION_VIEW)

        binding.tvFaceDetail.setOnClickListener {
            intent.data = Uri.parse(facebookURL)
            startActivity(intent)
        }

        binding.tvLinkedinDetails.setOnClickListener {
            intent.data = Uri.parse(linkedInURL)
            startActivity(intent)
        }
    }

    /*
     * Bind Member Detail layout with Member Dto Data from navigation arguments
     */
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