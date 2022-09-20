package com.melvin.ongandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.view.adapter.HorizontalAdapter
import com.melvin.ongandroid.view.adapter.NewsAdapter
import com.melvin.ongandroid.view.adapter.TestimonialAdapter


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var testimonialAdapter: TestimonialAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HorizontalAdapter(listOf())
        binding.rvWelcome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvWelcome.adapter = adapter
        setUpTestimonialRecyclerView()
        val adapternew = NewsAdapter(listOf())
        binding.rvNews.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.rvNews.adapter = adapternew
        subscribeUi()

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


    /*
    Subscribe all adapters to observe viewModel LiveData
     */
    private fun subscribeUi() {

        //TODO SubscribeBienvenidosAdapter

        //TODO SubscribeNovedadesAdapter

        subscribeTestimonialAdapter()
    }

    /*
    Subscribe Testimonial adapter to observe viewModel LiveData
     */
    private fun subscribeTestimonialAdapter() {
        // TODO
    }

}