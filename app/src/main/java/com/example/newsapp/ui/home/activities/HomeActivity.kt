package com.example.newsapp.ui.home.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SearchView.OnCloseListener
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.ui.adapters.Category
import com.example.newsapp.ui.home.fragments.CategoriesFragment
import com.example.newsapp.ui.home.fragments.NewsFragment
import com.example.newsapp.ui.home.fragments.SearchFragment
import com.example.newsapp.ui.home.fragments.SettingsFragment
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.http.Query

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    var query: String? = null
    lateinit var categoriesFragment: CategoriesFragment
    lateinit var searchFragment: SearchFragment
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var category: Category
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDrawer()
        initFragments()
        initListeners()
        showFragment(categoriesFragment)
        binding.searchView.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.searchView.visibility = View.GONE
    }

    private fun initListeners() {
        binding.searchView.setOnClickListener {
            binding.titleTv.visibility = View.GONE
        }
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
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
        binding.searchView.setOnCloseListener {
            binding.searchView.onActionViewCollapsed()
            binding.titleTv.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, NewsFragment(category)).commit()
            true
        }
        binding.navigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.categories_menu_item) {
                showFragment(categoriesFragment)
            } else if (it.itemId == R.id.settings_menu_item) {
                showFragment(SettingsFragment())
            }
            binding.root.closeDrawers()
            true
        }
    }

    private fun showDrawer() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.root, R.string.drawer_open, R.string.drawer_close)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        binding.root.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        actionBarDrawerToggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
            .addToBackStack("").commit()
    }

    private fun initFragments() {
        categoriesFragment = CategoriesFragment {
            category = it
            binding.searchView.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, NewsFragment(it)).addToBackStack("").commit()
        }
        searchFragment = SearchFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}