package com.example.newsapp.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.retrofit.ApiManager
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.home.activities.CategoryDetailsActivity
import com.example.newsapp.ui.viewmodel.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: ViewModel
    val adapter = NewsAdapter(emptyList()) {
        val intent =
            Intent(this@SearchFragment.requireActivity(), CategoryDetailsActivity::class.java)
        intent.putExtra(Constants.NEWS, it)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchNewsRv.adapter = adapter
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        observeToObservers()
        if (arguments!=null){
            val query = requireArguments().getString(Constants.QUERY)
            viewModel.loadNews("", query!!)
        }
    }

    private fun observeToObservers(){
        viewModel.newsLiveData.observe(viewLifecycleOwner){
            adapter.updateNews(it!!)
        }
    }
}