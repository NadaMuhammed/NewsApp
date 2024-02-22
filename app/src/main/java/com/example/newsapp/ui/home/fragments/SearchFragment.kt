package com.example.newsapp.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.retrofit.ApiManager
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.home.CategoryDetailsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
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
        if (arguments!=null){
            val query = requireArguments().getString(Constants.QUERY)
            loadNewsWithSearch(query)
        }
    }

    private fun loadNewsWithSearch(query: String?) {
        if (query!=null){
            ApiManager.webServices.getNewsWithSearch(Constants.API_KEY,query).enqueue(object : Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful && !response.body()?.articles.isNullOrEmpty()){
                        adapter.updateNews(response.body()?.articles!!)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {

                }

            })
        }
    }
}