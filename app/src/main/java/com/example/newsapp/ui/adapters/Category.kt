package com.example.newsapp.ui.adapters

import com.example.newsapp.R

data class Category(
    val title: String,
    val id: String,
    val imageId: Int,
    val backgroundColorId: Int,
    val side: String
) {
    companion object {
        val categoriesList = listOf<Category>(Category("Sports","sports", R.drawable.sports, R.color.red, "left"),
            Category("Technology", "technology", R.drawable.technology, R.color.dark_blue, "right"),
            Category("Health","health", R.drawable.health, R.color.pink, "left"),
            Category("Business", "business", R.drawable.business, R.color.orange, "right"),
            Category("Entertainment", "entertainment", R.drawable.entertainment, R.color.light_blue, "left"),
            Category("Science", "science", R.drawable.science, R.color.yellow, "right")
        )
    }
}