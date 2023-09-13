package com.example.newsapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.ui.home.categories.CategoriesFragment
import com.example.newsapp.ui.home.categories.Category
import com.example.newsapp.ui.home.categoryDetails.CategoryDetailsFragment
import com.example.newsapp.ui.home.newsFragment.NewsFragment
import com.example.newsapp.ui.home.settingsFragment.SettingsFragment

class HomeActivity : AppCompatActivity(),
    CategoriesFragment.OnCategoryClickListener {
    override fun onCategoryClick(category: Category) {
        showCategoryDetailsFragment(category)
    }

    lateinit var viewBinding: ActivityHomeBinding
    val categoriesFragment = CategoriesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initViews()

    }

    private fun initViews() {
        val toggle = ActionBarDrawerToggle(
            this, viewBinding.root,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        viewBinding.root.addDrawerListener(toggle)
        toggle.syncState()
        viewBinding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.categories -> {
                    showCategoriesFragment();
                }
                R.id.settings -> {
                    showSettingsFragment()
                }
            }
            viewBinding.root.closeDrawers()
            return@setNavigationItemSelectedListener true;
        }
        viewBinding.menu.setOnClickListener {
            viewBinding.drawerLayout.open()
        }
        categoriesFragment.onCategoryClickListener = this
        showCategoriesFragment()
    }

    fun showSettingsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, SettingsFragment())
            .commit()
    }

    fun showCategoriesFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, categoriesFragment)
            .commit()
    }

    fun showCategoryDetailsFragment(category: Category) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                CategoryDetailsFragment.getInstance(category)
            )
            .addToBackStack(null)
            .commit()
    }
}