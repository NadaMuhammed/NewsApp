package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.CategoryItemBinding

class CategoriesAdapter(val categoriesList: List<Category>,val onClick: (Category)->Unit): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    class CategoriesViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = categoriesList.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.binding.categoryBtn.text = categoriesList[position].title
        holder.binding.categoryBtn.backgroundTintList = ContextCompat.getColorStateList(holder.binding.categoryBtn.context, categoriesList[position].backgroundColorId)
        holder.binding.categoryBtn.setCompoundDrawablesWithIntrinsicBounds(0, categoriesList[position].imageId, 0, 0)
        if (categoriesList[position].side == "left"){
            holder.binding.categoryBtn.setBackgroundDrawable(ContextCompat.getDrawable(holder.binding.categoryBtn.context, R.drawable.left_bg))
        } else {
            holder.binding.categoryBtn.setBackgroundDrawable(ContextCompat.getDrawable(holder.binding.categoryBtn.context, R.drawable.right_bg))
        }
        holder.binding.categoryBtn.setOnClickListener {
            onClick.invoke(categoriesList[position])
        }
    }
}