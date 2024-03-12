package com.example.newsapp.ui.home.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.ui.adapters.Category
import com.example.newsapp.ui.home.fragments.NewsDetailsFragment
import com.example.newsapp.ui.home.fragments.CategoriesFragment
import com.example.newsapp.ui.home.fragments.NewsFragment
import com.example.newsapp.ui.home.fragments.SearchFragment
import com.example.newsapp.ui.home.fragments.SettingsFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    var query: String? = null
    lateinit var categoriesFragment: CategoriesFragment
    lateinit var searchFragment: SearchFragment
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var category: Category
    lateinit var categoryDetailsFragment: NewsDetailsFragment
    lateinit var currentFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        showDrawer()
        initFragments()
        initListeners()
        showFragment(categoriesFragment)
        binding.searchView.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (currentFragment is CategoriesFragment)
            binding.searchView.visibility = View.GONE
    }

    private fun initListeners() {
        binding.searchView.setOnSearchClickListener {
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
            showFragment(NewsFragment(category){
                categoryDetailsFragment = NewsDetailsFragment(it)
                showFragment(categoryDetailsFragment)
            })
            true
        }
        binding.navigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.categories_menu_item) {
                showFragment(categoriesFragment)
            } else if (it.itemId == R.id.settings_menu_item) {
                showFragment(SettingsFragment())
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    private fun showDrawer() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open, R.string.drawer_close)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        actionBarDrawerToggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
            .addToBackStack("").commit()
        currentFragment = fragment
    }

    private fun initFragments() {
        categoriesFragment = CategoriesFragment {
            category = it
            binding.searchView.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, NewsFragment(it) { news ->
                    categoryDetailsFragment = NewsDetailsFragment(news)
                    showFragment(categoryDetailsFragment)
                }).addToBackStack("").commit()
        }
        searchFragment = SearchFragment{
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, NewsDetailsFragment(it)).addToBackStack("").commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}