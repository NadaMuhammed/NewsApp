package com.example.newsapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.ui.home.fragments.NewsFragment
import com.example.newsapp.ui.home.fragments.SearchFragment
import retrofit2.http.Query

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    var query: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showFragment(NewsFragment())
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val searchFragment = SearchFragment()
                val bundle = Bundle()
                bundle.putString(Constants.QUERY, query)
                searchFragment.arguments = bundle
                showFragment(searchFragment)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                query = p0
                return true
            }
        })
//        binding.searchView.setOnCloseListener {
//
//        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}