package com.example.newsapp.ui.home.newsFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.api.sourcesResponse.model.Source
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.ui.Constants
import com.example.newsapp.ui.home.categories.Category
import com.example.newsapp.ui.home.newsDetails.NewsDetailsActivity

class NewsFragment : Fragment() {

    companion object {
        fun getInstance(source: Source, category: Category): NewsFragment {
            val newsFragmentRef = NewsFragment()
            newsFragmentRef.source = source;
            newsFragmentRef.category = category
            return newsFragmentRef
        }
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var viewBinding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    lateinit var source: Source
    lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        viewModel.getNews(source)
    }

    private fun initObservers() {
        viewModel.progressBarLiveData
            .observe(viewLifecycleOwner){isVisible->
                viewBinding.progressBar.isVisible = isVisible
            }
        viewModel.articlesLiveData
            .observe(viewLifecycleOwner){newsList->
                newsAdapter.changeData(newsList)
            }
        viewModel.viewErrorLiveData
            .observe(viewLifecycleOwner){viewError->
                handleError(viewError)
            }
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter((null))
        viewBinding.newsRv.adapter = newsAdapter
        newsAdapter.onItemClickListener =
            NewsAdapter.OnItemClickListener { item ->
                val intent = Intent(requireActivity(), NewsDetailsActivity::class.java)
                intent.putExtra(Constants.NEWS_ITEM_KEY, item)
                intent.putExtra(Constants.CATEGORY, category)
                startActivity(intent)
            }
    }

}