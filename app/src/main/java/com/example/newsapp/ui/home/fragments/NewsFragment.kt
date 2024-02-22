package com.example.newsapp.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.newsapp.Constants
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.Source
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.api.retrofit.ApiManager
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.home.CategoryDetailsActivity
import com.example.newsapp.ui.home.HomeActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment() : Fragment() {
    lateinit var binding: FragmentNewsBinding
    var adapter = NewsAdapter(emptyList()) {
        val intent =
            Intent(this@NewsFragment.requireActivity(), CategoryDetailsActivity::class.java)
        intent.putExtra(Constants.NEWS, it)
        startActivity(intent)
    }

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
        initListeners()
        binding.newsRv.adapter = adapter
    }

    private fun loadSources() {
        changeProgressBarVisibility(true)
        changeErrorVisibility(false)
        ApiManager.webServices.getSources(Constants.API_KEY, "sports")
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    changeProgressBarVisibility(false)
                    if (response.isSuccessful) {
                        showTabs(response.body()?.sources)
                    } else {
//                        val errorResponse = Gson().fromJson(
//                            response.errorBody()?.string(),
//                            SourcesResponse::class.java
//                        )
                        changeErrorVisibility(true)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    changeProgressBarVisibility(false)
                    changeErrorVisibility(true)
                }

            })
    }

    private fun initListeners() {
        binding.errorView.reloadBtn.setOnClickListener {
            loadSources()
        }
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val sourceId = tab?.tag as String
                loadNews(sourceId)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun loadNews(sourceId: String) {
        changeErrorVisibility(false)
        ApiManager.webServices.getNews(Constants.API_KEY, sourceId)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    changeProgressBarVisibility(false)
                    if (response.isSuccessful && response.body()?.articles?.isNotEmpty() == true) {
                        adapter.updateNews(response.body()?.articles!!)
                    } else {
                        changeErrorVisibility(true)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    changeProgressBarVisibility(false)
                    changeErrorVisibility(true)
                }

            })
    }

    private fun showTabs(sources: List<Source>?) {
        sources?.forEach { source ->
            val tab = binding.tabLayout.newTab()
            tab.text = source.name
            tab.tag = source.id
            binding.tabLayout.addTab(tab)
        }
    }

    private fun changeErrorVisibility(isVisible: Boolean) {
        binding.errorView.root.isVisible = isVisible
    }

    private fun changeProgressBarVisibility(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }
}