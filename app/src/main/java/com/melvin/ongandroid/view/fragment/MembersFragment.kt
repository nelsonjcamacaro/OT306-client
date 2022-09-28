package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentMembersBinding
import com.melvin.ongandroid.model.nosotrosActivities.model.MemberDto
import com.melvin.ongandroid.utils.LoadingSpinner
import com.melvin.ongandroid.utils.ResultState
import com.melvin.ongandroid.view.adapter.MembersAdapter
import com.melvin.ongandroid.viewmodel.MembersViewModel
import com.melvin.ongandroid.viewmodel.MembersViewModelFactory
import com.melvin.ongandroid.viewmodel.ViewModelFactory

const val TAG = "MembersFragment"

@Suppress("UNCHECKED_CAST")
class MembersFragment : Fragment() {

    private var _binding: FragmentMembersBinding? = null
    private val binding get() = _binding!!
    private lateinit var membersAdapter: MembersAdapter
    private lateinit var loadingSpinner: LoadingSpinner

    private val membersViewModel: MembersViewModel by viewModels(
        factoryProducer = { MembersViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingSpinner = LoadingSpinner()

        val manager = MembersAdapter(listOf())
        binding.membersRV.layoutManager = GridLayoutManager(context,2)
        binding.membersRV.adapter = manager

        subscribeUi()
    }

    private fun subscribeUi() {
        membersViewModel.membersResponse.observe( viewLifecycleOwner, Observer { resultState ->
            when (resultState) {
                is ResultState.Loading -> {
                    Log.d(TAG, "Data is loading")
                    setLoadingSpinner(true)
                }
                is ResultState.Success -> {
                    Log.d(TAG, "Data successfully retrieved")
                    Log.d(TAG, resultState.data.toString())
                    setLoadingSpinner(false)
                    val membersAdapter = MembersAdapter(resultState.data as List<MemberDto>)
                    binding.membersRV.adapter = membersAdapter
                }
                is ResultState.Error -> {
                    Log.d(TAG, resultState.exception.toString())
                    setLoadingSpinner(false)
                }
            }
        })
    }

    private fun setLoadingSpinner(isLoading: Boolean) {
        if (isLoading) {
            loadingSpinner.start(binding.membersImageLogo)
        } else {
            loadingSpinner.stop(binding.membersImageLogo)
        }
    }
}