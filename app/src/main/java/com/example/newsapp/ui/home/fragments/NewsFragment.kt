package com.example.newsapp.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.Source
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.ui.adapters.Category
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.viewmodel.ViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class NewsFragment(val category: Category, val onNewsClick: (News)-> Unit) : Fragment() {
    lateinit var binding: FragmentNewsBinding
    var adapter = NewsAdapter(emptyList()) {
        onNewsClick.invoke(it)
    }
    lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.loadSources(category)
        observeToObservers()
        initListeners()
        binding.newsRv.adapter = adapter
    }

    private fun initListeners() {
        binding.errorView.reloadBtn.setOnClickListener {
            viewModel.loadSources(category)
        }
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val sourceId = tab?.tag as String
                viewModel.loadNews(sourceId, "")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

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
    private fun observeToObservers(){
        viewModel.sourcesLiveData.observe(viewLifecycleOwner) {
            showTabs(it)
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner){
            if (it != null) {
                adapter.updateNews(it)
            }
        }
    }
}