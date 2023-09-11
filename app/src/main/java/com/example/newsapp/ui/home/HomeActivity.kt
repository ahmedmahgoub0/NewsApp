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

class HomeActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityHomeBinding
    private val categoryFragmentRef = CategoriesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initViews()
    }

    private fun initViews() {
        val toggle = ActionBarDrawerToggle(
            this, viewBinding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        viewBinding.root.addDrawerListener(toggle)
        toggle.syncState()
        viewBinding.navView.setNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.categories -> {
                    showFragment(CategoriesFragment())
                }
                R.id.settings->{
                    showFragment(CategoriesFragment())
                }
            }
            viewBinding.root.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        viewBinding.menu.setOnClickListener {
            viewBinding.drawerLayout.open()
        }

//        categoryFragmentRef.onCategoryClickListener =
//            CategoriesFragment.OnCategoryClickListener { category->
//                showCategoryDetailsActivity(category)
//            }

        showFragment(NewsFragment())
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.fragmentContainer.id, fragment)
            .commit()
    }


    private fun showCategoryDetailsActivity(category: Category){
        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.fragmentContainer.id, CategoryDetailsFragment.getInstance(category))
            .addToBackStack(null)
            .commit()
    }
}