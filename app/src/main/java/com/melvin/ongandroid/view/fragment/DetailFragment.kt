package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        setupToolBarNavigation()
    }

    // toolBar backstack navigation
    private fun setupToolBarNavigation() {
        binding.toolbarDetail.setOnClickListener {
            val action =  DetailFragmentDirections.actionDetailFragmentToMembersFragment()
            findNavController().navigate(action)
        }
    }
}