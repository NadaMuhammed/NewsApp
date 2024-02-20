package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.Constants
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.api.retrofit.ApiManager
import com.example.newsapp.databinding.FragmentCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment() {
    lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ApiManager.webServices.getSources(Constants.API_KEY, "sports").enqueue(object : Callback<SourcesResponse>{
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {

            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}