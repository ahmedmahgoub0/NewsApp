package com.example.newsapp.ui.home.categoryDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.api.ApiConstants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.sourcesResponse.model.Source
import com.example.newsapp.api.sourcesResponse.model.SourcesResponse
import com.example.newsapp.databinding.FragmentCategoryDetailsBinding
import com.example.newsapp.ui.home.categories.Category
import com.example.newsapp.ui.home.newsFragment.NewsFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsFragment : Fragment() {
    lateinit var viewBinding: FragmentCategoryDetailsBinding
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
        loadNewsSources();
    }

    fun changeNewsFragment(source: Source) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_news_container, NewsFragment.getInstance(source))
            .commit()
    }

    private fun loadNewsSources() {
        showLoadingLayout()
        ApiManager
            .getApis()
            .getSources(ApiConstants.API_KEY, category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    viewBinding.progressBar.isVisible = false

                    if (response.isSuccessful) {
                        bindSourcesInTabLayout(response.body()?.sources)
                    } else {
                        val gson = Gson()
                        val errorResponse =
                            gson.fromJson(
                                response.errorBody()?.string(),
                                SourcesResponse::class.java
                            );
                        showErrorLayout(errorResponse.message);
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    viewBinding.progressBar.isVisible = false
                    showErrorLayout(t.localizedMessage);
                }
            })
    }

    private fun showLoadingLayout() {
        viewBinding.progressBar.isVisible = true
    }

    private fun showErrorLayout(message: String?) {
        viewBinding.progressBar.isVisible = false;
    }

    fun bindSourcesInTabLayout(sourcesList: List<Source?>?) {
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

    lateinit var category: Category

    companion object {
        fun getInstance(category: Category): CategoryDetailsFragment {
            val fragment = CategoryDetailsFragment()
            fragment.category = category
            return fragment
        }
    }
}