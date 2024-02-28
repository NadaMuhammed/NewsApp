package com.example.newsapp.ui.home.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapp.Constants
import com.example.newsapp.api.model.News
import com.example.newsapp.databinding.ActivityCategoryDetailsBinding
import java.text.SimpleDateFormat


class CategoryDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryDetailsBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val news =  intent.getSerializableExtra(Constants.NEWS) as News
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