package com.example.newsapp.ui.home.newsFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.api.ApiConstants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.newsResponse.News
import com.example.newsapp.api.newsResponse.NewsResponse
import com.example.newsapp.api.sourcesResponse.model.Source
import com.example.newsapp.databinding.FragmentNewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    companion object {
        fun getInstance(source: Source): NewsFragment {
            val newNewsFragment = NewsFragment()
            newNewsFragment.source = source;
            return newNewsFragment
        }
    }

    private lateinit var viewBinding: FragmentNewsBinding
    lateinit var newsAdapter: NewsAdapter
    lateinit var source: Source

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentNewsBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getNews();
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter((null))
        viewBinding.newsRv.adapter = newsAdapter
    }

    private fun getNews() {

        showLoadingLayout()
        ApiManager
            .getApis()
            .getNews(ApiConstants.API_KEY, source.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        bindNewsList(response.body()?.articles)
                        return
                    }
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.string(), NewsResponse::class.java
                    )
                    showErrorLayout(errorResponse.message)
                }
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    showErrorLayout(t.localizedMessage)
                }
            })
    }

    private fun bindNewsList(articles: List<News?>?) {
        viewBinding.progressBar.isVisible = false
        newsAdapter.changeData(articles)
    }

    private fun showLoadingLayout() {
        viewBinding.progressBar.isVisible = true
    }

    private fun showErrorLayout(message: String?) {
        viewBinding.progressBar.isVisible = false;
    }
}