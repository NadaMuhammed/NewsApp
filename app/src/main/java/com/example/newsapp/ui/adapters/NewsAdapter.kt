package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.api.model.News
import com.example.newsapp.databinding.NewsItemBinding

class NewsAdapter(var news: List<News>?) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(var binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = news?.size!!

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(news?.get(position)?.urlToImage).into(holder.binding.newsImv)
        holder.binding.newsSourceTv.text = news?.get(position)?.source?.name
        holder.binding.newsTimeTv.text = news?.get(position)?.publishedAt
        holder.binding.newsTitleTv.text = news?.get(position)?.title
    }

    fun updateNews(newNews: List<News>){
        this.news = newNews
        notifyDataSetChanged()
    }
}