package com.melvin.ongandroid.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.businesslogic.news.GetNewsUseCase
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.inicioActivitys.Activity
import com.melvin.ongandroid.model.Testimonial
import com.melvin.ongandroid.model.news.NewsRepository
import com.melvin.ongandroid.model.news.NewsViewState
import com.melvin.ongandroid.model.news.RetrofitClient
import com.melvin.ongandroid.utils.AppConstants
import com.melvin.ongandroid.utils.Extensions.logEventInFirebase
import com.melvin.ongandroid.utils.LoadingSpinner
import com.melvin.ongandroid.utils.ResultState
import com.melvin.ongandroid.view.adapter.TestimonialAdapter
import com.melvin.ongandroid.view.adapter.HorizontalAdapter
import com.melvin.ongandroid.view.adapter.NewsAdapter
import com.melvin.ongandroid.viewmodel.ActivityViewModel
import com.melvin.ongandroid.viewmodel.ActivityViewModelFactory
import com.melvin.ongandroid.viewmodel.TestimonialsViewModel
import com.melvin.ongandroid.viewmodel.TestimonialsViewModelFactory
import com.melvin.ongandroid.viewmodel.news.NewsViewModel
import com.melvin.ongandroid.viewmodel.news.NewsViewModelFactory

@Suppress("UNCHECKED_CAST")
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var testimonialAdapter: TestimonialAdapter
    private lateinit var horizontalAdapter: HorizontalAdapter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var loadingSpinner: LoadingSpinner

    private lateinit var firebaseAnalytic: FirebaseAnalytics

    private val newsViewModel by viewModels<NewsViewModel> {
        NewsViewModelFactory(GetNewsUseCase(NewsRepository(RetrofitClient.webservice)))
    }

    private val viewModel: TestimonialsViewModel by viewModels(
        factoryProducer = { TestimonialsViewModelFactory() })

    private val viewModels: ActivityViewModel by viewModels(
        factoryProducer = { ActivityViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytic = FirebaseAnalytics.getInstance(requireContext())
        loadingSpinner = LoadingSpinner()
        setupRecyclerViews()
        subscribeUi()
    }

    /*
     * Setup all Recycler Views
     */
    private fun setupRecyclerViews() {
        setupRvNews()
        setupRvActivity()
        setUpTestimonialRecyclerView()
    }

    private fun setupActivityAdapter(activitiesList : List<Activity>){
        binding.rvWelcome.adapter = HorizontalAdapter(activitiesList)
    }

    private fun setupRvActivity(){
        horizontalAdapter = HorizontalAdapter(listOf())
        binding.rvWelcome.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = horizontalAdapter
        }
    }

    // news adapter setup
    private fun setupRvNews() {
        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    /*
     * Set initial configuration for Testimonial Recycler View
     */
    private fun setUpTestimonialRecyclerView() {
        binding.testimonialsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    /*
     * Subscribe all adapters to observe viewModel LiveData
     */
    private fun subscribeUi() {
        subscribeTestimonialAdapter()
        newsUpdateUI() // load news
        subscribeActivityAdapter()
    }

    private fun subscribeActivityAdapter(){
        viewModels.activitiesResultState.observe(viewLifecycleOwner, Observer { resulState->
            when(resulState){
                is ResultState.Loading ->{
                    setLoadingSpinner(true)
                }
                is ResultState.Success ->{
                    val activityList = (resulState.data as? List<Activity>) ?: emptyList()
                    if (activityList.isNotEmpty()) setupActivityAdapter(activityList)
                    setLoadingSpinner(false)
                }
                is ResultState.Error ->{

                }

            }


        })
    }

    private fun newsUpdateUI() {
        newsViewModel.newsData.observe(viewLifecycleOwner, Observer { viewState ->
            updateUI(viewState)
        })
        newsViewModel.loadNews()
    }

    // news update data
    private fun updateUI(viewState: NewsViewState) {
        when (viewState) {
            is NewsViewState.Loading -> {
                // show progress bar
                setLoadingSpinner(true)
            }
            is NewsViewState.Content -> {
                if (viewState.content.isEmpty()) {
                    binding.rvNews.visibility = View.GONE
                }

                val listSize = viewState.content.size
                if ( listSize >= 4) {
                    newsAdapter.setNewsData(viewState.content.subList(0, 4))
                } else {
                    newsAdapter.setNewsData(viewState.content.subList(0, listSize))
                }
                
                setLoadingSpinner(false)
                logEventInFirebase(firebaseAnalytic, "last_news_retrieve_success")
            }
            is NewsViewState.Error -> {
                binding.rvNews.visibility = View.GONE
                // show error message and reload data
                errorSnackBar(AppConstants.SET_MESSAGE) {
                    newsViewModel.loadNews()
                }
                logEventInFirebase(firebaseAnalytic, "last_news_retrieve_error")
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
     * Subscribe Testimonial adapter to observe viewModel LiveData
     */
    private fun subscribeTestimonialAdapter() {
        viewModel.testimonialsResultState.observe(viewLifecycleOwner, Observer { resultState ->
            when (resultState) {
                is ResultState.Loading -> {
                    Log.d(com.melvin.ongandroid.view.fragment.TAG, "Data is loading")
                    setLoadingSpinner(true)
                }
                is ResultState.Success -> {
                    if (resultState.data == null) {
                       // showErrorSnackBar()
                    } else {
                        val testimonialsList = (resultState.data as? List<Testimonial>) ?: emptyList()
                        if (testimonialsList.isNotEmpty()) setTestimonialsAdapter(testimonialsList)
                    }
                    logEventInFirebase(firebaseAnalytic,"testimonios_retrieve_success")
                }
                is ResultState.Error -> {
                    Log.e(com.melvin.ongandroid.view.fragment.TAG, resultState.exception.toString())
                    //showErrorSnackBar()
                    logEventInFirebase(firebaseAnalytic,"testimonies_retrieve_error")
                }
            }

        })

        }

    private fun setTestimonialsAdapter(testimonial: List<Testimonial>){
        setLoadingSpinner(false)
        testimonialAdapter = TestimonialAdapter()
        testimonialAdapter.submitList(testimonial)
        binding.testimonialsRecyclerView.adapter = testimonialAdapter
    }

    private fun setLoadingSpinner(isLoading: Boolean) {
        if (isLoading) {
            loadingSpinner.start(binding.imageLogo)
            binding.layoutNews.visibility = View.GONE
            binding.layoutTestimonial.visibility = View.GONE
            binding.rvWelcome.visibility = View.GONE
        } else {
            loadingSpinner.stop(binding.imageLogo)
            binding.layoutNews.visibility = View.VISIBLE
            binding.layoutTestimonial.visibility = View.VISIBLE
            binding.rvWelcome.visibility = View.VISIBLE
        }
    }
}