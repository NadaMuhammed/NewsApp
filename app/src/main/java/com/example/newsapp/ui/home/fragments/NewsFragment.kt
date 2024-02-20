package com.example.newsapp.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.Constants
import com.example.newsapp.api.model.Source
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.api.retrofit.ApiManager
import com.example.newsapp.databinding.FragmentNewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {
    lateinit var binding: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSources()
    }

    private fun loadSources() {
        ApiManager.webServices.getSources(Constants.API_KEY, "sports").enqueue(object : Callback<SourcesResponse>{
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                if (response.isSuccessful){
                    showTabs(response.body()?.sources)
                } else{
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), SourcesResponse::class.java)
                    showErrorViews(errorResponse)
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showErrorViews(sourcesResponse: SourcesResponse) {

    }

    private fun showTabs(sources: List<Source>?) {
        sources?.forEach {source->
            val tab = binding.tabLayout.newTab()
            tab.text = source.name
            binding.tabLayout.addTab(tab)
        }
    }
}