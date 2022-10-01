package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.melvin.ongandroid.businesslogic.news.GetNewsUseCase
import com.melvin.ongandroid.databinding.FragmentNovedadesBinding
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.model.news.NewsRepository
import com.melvin.ongandroid.model.news.NewsViewState
import com.melvin.ongandroid.model.news.RetrofitClient
import com.melvin.ongandroid.utils.Extensions.errorSnackBar
import com.melvin.ongandroid.utils.LoadingSpinner
import com.melvin.ongandroid.view.adapter.NovedadesAdapter
import com.melvin.ongandroid.viewmodel.news.NewsViewModel
import com.melvin.ongandroid.viewmodel.news.NewsViewModelFactory

class NovedadesFragment : Fragment() {
    private var _binding: FragmentNovedadesBinding? = null
    private val binding get() = _binding!!
    private lateinit var novedadesAdapter: NovedadesAdapter
    private lateinit var loadingSpinner: LoadingSpinner
    private val newsViewModel by viewModels<NewsViewModel> {
        NewsViewModelFactory(GetNewsUseCase(NewsRepository(RetrofitClient.webservice)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNovedadesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingSpinner = LoadingSpinner()

        subscribeUi()

    }

    /*
     * Initiate the request to the server from newsViewModel and
     * subscribe Ui to News View State from News ViewModel Live Data
     */
    private fun subscribeUi() {
        newsViewModel.loadNews()
        newsViewModel.newsData.observe(viewLifecycleOwner, Observer { viewState ->
            when (viewState) {
                is NewsViewState.Loading -> setLoadingSpinner(true)
                is NewsViewState.Content -> {
                    if (viewState.content.isEmpty()) {
                        showErrorSnackBar()
                    } else {
                        setUpNewsRecyclerView(viewState.content)
                    }
                }
                is NewsViewState.Error ->  showErrorSnackBar()
            }
        })
    }

    /*
     * Call it when server data is retrieved successfully.
     * Set data and configuration for Novedades Recycler View
     */
    private fun setUpNewsRecyclerView(news : List<NewsModel>) {
        setLoadingSpinner(false)
        novedadesAdapter = NovedadesAdapter(news)
        binding.rvNovedades.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = novedadesAdapter
        }
    }

    /*
     * Call with isLoading = true if Result State is Loading. Function will start the animation.
     * Call with isLoading = false if Result State isn´t Loading. Function will stop the animation
     * and restore the image logo
     */
    private fun setLoadingSpinner(isLoading: Boolean) {
        if (isLoading) {
            loadingSpinner.start(binding.imageLogo2)
        } else {
            loadingSpinner.stop(binding.imageLogo2)
        }
    }

    /*
     * Call it when have an error result from newsViewModel.newsData
     * or when the news list is empty.
     * Function will stop the loading animation, inform to user the error and give him a button
     * to retry the petition.
     */
    private fun showErrorSnackBar() {
        setLoadingSpinner(false)
        errorSnackBar(binding.root) {
            newsViewModel.loadNews()
        }
    }

}