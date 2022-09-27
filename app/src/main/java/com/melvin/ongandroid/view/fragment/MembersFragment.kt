package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.databinding.FragmentMembersBinding
import com.melvin.ongandroid.model.NosotrosActivities.MembersList
import com.melvin.ongandroid.view.adapter.MembersAdapter


class MembersFragment : Fragment() {

    private var _binding: FragmentMembersBinding? = null
    private val binding get() = _binding!!
    private lateinit var membersAdapter: MembersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = MembersAdapter(listOf())
        binding.membersRV.layoutManager = GridLayoutManager(context,2)
        binding.membersRV.adapter = manager
    }


}