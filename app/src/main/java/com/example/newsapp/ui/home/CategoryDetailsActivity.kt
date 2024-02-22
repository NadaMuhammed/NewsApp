package com.example.newsapp.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapp.api.model.News
import com.example.newsapp.databinding.ActivityCategoryDetailsBinding


class CategoryDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryDetailsBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val news =  intent.getSerializableExtra("News") as News
        Glide.with(this).load(news.urlToImage).into(binding.detailsNewsImv)
        binding.detailsNewsTimeTv.text = news.publishedAt
        binding.detailsNewsDescriptionTv.text = news.description
        binding.detailsNewsSourceTv.text = news.source?.name
        binding.detailsNewsTitleTv.text = news.title
    }

}