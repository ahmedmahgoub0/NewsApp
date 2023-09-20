package com.example.newsapp.ui.home.homeActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.ui.home.categories.CategoriesFragment
import com.example.newsapp.ui.home.categories.Category
import com.example.newsapp.ui.home.categoryDetails.CategoryDetailsFragment
import com.example.newsapp.ui.home.settingsFragment.SettingsFragment

class HomeActivity : AppCompatActivity(),
    CategoriesFragment.OnCategoryClickListener {

    private lateinit var viewBinding: ActivityHomeBinding
    private val categoriesFragment = CategoriesFragment()

    override fun onCategoryClick(category: Category) {
        viewBinding.toolbarTv.text = category.name
        showCategoryDetailsFragment(category)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initViews()
    }


    private fun initViews() {
        viewBinding.toolbarTv.text = "News App"
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
                    showCategoryFragment(categoriesFragment);
                }
                R.id.settings -> {
                    showSettingsFragment(SettingsFragment())
                }
            }
            viewBinding.root.closeDrawers()
            return@setNavigationItemSelectedListener true;
        }
        viewBinding.menu.setOnClickListener {
            viewBinding.drawerLayout.open()
        }
        categoriesFragment.onCategoryClickListener = this
        showCategoryFragment(categoriesFragment)
    }

    private fun showCategoryFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun showSettingsFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun showCategoryDetailsFragment(category: Category) {
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