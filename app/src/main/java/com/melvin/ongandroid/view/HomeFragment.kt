package com.melvin.ongandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.view.adapter.TestimonialAdapter
import com.melvin.ongandroid.view.adapter.HorizontalAdapter
import com.melvin.ongandroid.view.adapter.NewsAdapter
import com.melvin.ongandroid.viewmodel.ActivityViewModel
import com.melvin.ongandroid.viewmodel.ActivityViewModelFactory
import com.melvin.ongandroid.viewmodel.TestimonialsViewModel
import com.melvin.ongandroid.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var testimonialAdapter: TestimonialAdapter
    private lateinit var newsAdapter: NewsAdapter

    /** firebaseAnalytis **/
    private lateinit var firebaseAnalytic: FirebaseAnalytics


    private val viewModel : TestimonialsViewModel by viewModels(
        factoryProducer ={ ViewModelFactory() })

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

        val adapter = HorizontalAdapter(listOf())
        binding.rvWelcome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvWelcome.adapter = adapter

        
        val adapternew = NewsAdapter(listOf())
        binding.rvNews.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.rvNews.adapter = adapternew

        setUpTestimonialRecyclerView()
        subscribeUi()
        viewModels.slides.observe(viewLifecycleOwner){activitiesList ->
            if(activitiesList != null){
                adapter.activitiesList = activitiesList
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(context,"Incio - Error general ",Toast.LENGTH_SHORT).show()
            }

        }
    }

    /*
    Set initial configuration for Testimonial Recycler View
     */
    private fun setUpTestimonialRecyclerView() {
        testimonialAdapter = TestimonialAdapter()
        binding.testimonialsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            adapter = testimonialAdapter
        }
    }

    /** firebase analytics **/
    private fun logEvents() {
        //last_news_see_more_pressed': Al presionar la flecha "Ver más" en listado de "Últimas novedades"
        val bundle = Bundle()
        bundle.putString("eventLog", "last_news_see_more_pressed")
        firebaseAnalytic.logEvent("last_news_see_more_pressed", bundle)

        //testimonies_see_more_pressed: Al presionar la flecha "Ver más" en listado de "Testimonios"
        //slider_retrieve_success': En caso de que el GET del slider haya sido satisfactorio
        //slider_retrieve_error': En caso de que el GET del slider falle
        //last_news_retrieve_success': En caso de que el GET de noticias sea satisfactorio
        //last_news_retrieve_error': En caso de que el GET de noticias falle
        //testimonios_retrieve_success': En caso de que el GET de testimonios sea satisfactorio
        //testimonies_retrieve_error': En caso de que el GET de tesimonios falle
    }


    /*
    Subscribe all adapters to observe viewModel LiveData
     */
    private fun subscribeUi() {
        subscribeTestimonialAdapter()
    }

    /*
    Subscribe Testimonial adapter to observe viewModel LiveData
     */
    private fun subscribeTestimonialAdapter() {

        viewModel.testimonialsList.observe(viewLifecycleOwner, Observer { testimonial ->
            if (testimonial != null){
            testimonialAdapter.submitList(testimonial)
        }
        else{
            Toast.makeText(context,"error al pedir los testimonios",Toast.LENGTH_SHORT).show()
        } })

        viewModel.viewModelScope.launch {
            viewModel.loadTestimonials()
        }
    }
}