package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.databinding.FragmentActivitiesBinding
import com.melvin.ongandroid.model.inicioActivitys.Activity
import com.melvin.ongandroid.utils.AppConstants
import com.melvin.ongandroid.utils.LoadingSpinner
import com.melvin.ongandroid.utils.ResultState
import com.melvin.ongandroid.view.adapter.ActivitiesAdapter
import com.melvin.ongandroid.viewmodel.ActivityViewModel
import com.melvin.ongandroid.viewmodel.ActivityViewModelFactory

@Suppress("UNCHECKED_CAST")
class ActivitiesFragment : Fragment() {
    private var _binding: FragmentActivitiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var activitiesAdapter: ActivitiesAdapter
    private lateinit var loadingSpinner: LoadingSpinner

    private val viewModel: ActivityViewModel by viewModels(
        factoryProducer = { ActivityViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingSpinner = LoadingSpinner()
        setUpActivitiesRecyclerView()
        subscribeUi()

    }

    /*
    Set initial configuration for Activities Recycler View
     */
    private fun setUpActivitiesRecyclerView() {
        activitiesAdapter = ActivitiesAdapter()
        binding.recyclerViewActivities.apply {
            layoutManager = LinearLayoutManager(
                this@ActivitiesFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = activitiesAdapter
        }
    }

    /*
   Subscribe Activities adapter to observe viewModel LiveData
    */
    private fun subscribeUi() {
        viewModel.activitiesResultState.observe(this.viewLifecycleOwner) { resulState->
            when(resulState){
                is ResultState.Loading ->{
                    setLoadingSpinner(true)
                }
                is ResultState.Success ->{
                    val activityList = (resulState.data as? List<Activity>) ?: emptyList()
                    if (activityList.isNotEmpty()) activitiesAdapter.submitList(activityList)
                    setLoadingSpinner(false)
                }
                is ResultState.Error ->{
                    errorSnackBar(AppConstants.SET_MESSAGE){}
                }
            }
        }
    }

    // error message snackBar
    private fun errorSnackBar(message: String, reloadData: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(AppConstants.POSITIVE_BUTTON) {
                reloadData()
            }
            .show()
    }

    /*
     * Call with isLoading = true if Result State is Loading. Function will start the animation.
     * Call with isLoading = false if Result State isn´t Loading. Function will stop the animation
     * and restore the image logo
     */
    private fun setLoadingSpinner(isLoading: Boolean) {
        if (isLoading) {
            loadingSpinner.start(binding.imageLogo)
        } else {
            loadingSpinner.stop(binding.imageLogo)
        }
    }
}