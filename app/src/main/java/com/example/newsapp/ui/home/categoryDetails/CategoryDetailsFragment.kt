package com.example.newsapp.ui.home.categoryDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.api.ApiConstants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.sourcesResponse.model.Source
import com.example.newsapp.api.sourcesResponse.model.SourcesResponse
import com.example.newsapp.databinding.FragmentCategoryDetailsBinding
import com.example.newsapp.ui.home.categories.Category
import com.example.newsapp.ui.home.newsFragment.NewsFragment
import com.example.newsapp.ui.home.newsFragment.handleError
import com.example.newsapp.ui.home.newsFragment.showMessage
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsFragment : Fragment() {
    companion object {
        fun getInstance(category: Category): CategoryDetailsFragment {
            val fragment = CategoryDetailsFragment()
            fragment.category = category
            return fragment
        }
    }

    lateinit var viewModel: CategoriesDetailsViewModel
    lateinit var viewBinding: FragmentCategoryDetailsBinding
    lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesDetailsViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCategoryDetailsBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.loadNewsSources(category);
    }

    private fun initObservers() {
        viewModel.progressBarLiveData
            .observe(viewLifecycleOwner){isVisible->
                viewBinding.progressBar.isVisible = isVisible
            }
        viewModel.sourcesLiveData
            .observe(viewLifecycleOwner){sources->
                bindSourcesInTabLayout(sources)
            }
        viewModel.viewErrorLiveData
            .observe(viewLifecycleOwner){viewError->
                handleError(viewError)
            }
    }

    fun changeNewsFragment(source: Source) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_news_container, NewsFragment.getInstance(source))
            .commit()
    }

    private fun bindSourcesInTabLayout(sourcesList: List<Source?>?) {
        sourcesList?.forEach { source ->
            val tab = viewBinding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source
            viewBinding.tabLayout.addTab(tab)
        }
        viewBinding.tabLayout
            .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    changeNewsFragment(source)
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    changeNewsFragment(source)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }
            })
        viewBinding.tabLayout.getTabAt(0)?.select()
    }

}