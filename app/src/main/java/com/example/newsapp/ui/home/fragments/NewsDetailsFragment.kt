package com.example.newsapp.ui.home.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.newsapp.api.model.News
import com.example.newsapp.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment(val news: News) : Fragment() {
    lateinit var binding: FragmentNewsDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(news.urlToImage).into(binding.detailsNewsImv)
        binding.detailsNewsTimeTv.text = news.publishedAt
//        SimpleDateFormat("").parse(news.publishedAt)
        binding.detailsNewsDescriptionTv.text = news.description
        binding.detailsNewsSourceTv.text = news.source?.name
        binding.detailsNewsTitleTv.text = news.title
        binding.detailsViewArticleTxt.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(news.url))
            startActivity(intent)
        }
    }
}