package com.example.newsapp.ui.home.newsFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.newsResponse.ArticlesItem
import com.example.newsapp.api.newsResponse.NewsResponse
import com.example.newsapp.api.sourcesResponse.model.Source
import com.example.newsapp.api.sourcesResponse.model.SourcesResponse
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.ui.Constants
import com.example.newsapp.ui.home.newsDetails.NewsDetailsActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    lateinit var viewBinding: FragmentNewsBinding
    lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getNewsSources()
    }

    private fun initViews() {
        adapter = NewsAdapter()
        viewBinding.newsRv.adapter = adapter
        adapter.onItemClickListener = NewsAdapter.OnItemClickListener { item->
            val intent = Intent(requireActivity(), NewsDetailsActivity::class.java)
            intent.putExtra(Constants.NEWS_ITEM_KEY, item)
            startActivity(intent)
        }
    }

    private fun getNewsSources() {
        viewBinding.progressBar.isVisible = true
        val resources = ApiManager.getApi()
            .getSources()
            .enqueue(object: Callback<SourcesResponse>{
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    viewBinding.progressBar.isVisible = false
                    if(response.isSuccessful){
                        bindTabs(response.body()?.sources)
                    } else {
                        val errorMessage =
                            Gson().fromJson(response.errorBody()?.string(), SourcesResponse::class.java)
                        handleError(errorMessage.message){
                            getNewsSources()
                        }
                    }
                }
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    viewBinding.progressBar.isVisible = false
                    handleError(t) {
                        getNewsSources()
                    }
                }
            })
    }

    private fun getNews(sourceId: String?) {
        viewBinding.progressBar.isVisible = true
        ApiManager
            .getApi()
            .getNews(sources = sourceId?:"")
            .enqueue(object: Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    viewBinding.progressBar.isVisible = false
                    if(response.isSuccessful){
                        adapter.bindNews(response.body()?.articles)
                        return
                    }
                    val errorMessage = Gson().fromJson(response.errorBody()?.string(),
                        NewsResponse::class.java)
                    handleError(errorMessage.message){
                        getNews(sourceId)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    viewBinding.progressBar.isVisible = false
                    handleError(t) {
                        getNews(sourceId)
                    }
                }
            })
    }
    private fun bindTabs(sources: List<Source?>?) {
        if(sources==null) return
        sources.forEach { source->
            val tab = viewBinding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source
            viewBinding.tabLayout.addTab(tab)
        }
        viewBinding.tabLayout.addOnTabSelectedListener(
            object: OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    getNews(source.id)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    getNews(source.id)
                }
            }
        )
        viewBinding.tabLayout.getTabAt(0)?.select()
    }


    fun interface OnTryAgainClickListener{
        fun onTryAgainClick()
    }
    private fun handleError(t:Throwable, onClick: OnTryAgainClickListener){
        showMessage(
            t.localizedMessage?:"something went wrong",
            posActionName = "try again",
            posAction = {dialogInterface, i ->
                dialogInterface.dismiss()
                onClick.onTryAgainClick()
            },
            negActionName = "cancel",
            negAction = {dialogInterface, i ->
                dialogInterface.dismiss()
            }
        )
    }
    private fun handleError(message: String?, onClick: OnTryAgainClickListener){
        showMessage(
            message?:"something went wrong",
            posActionName = "try again",
            posAction = {dialogInterface, i ->
                dialogInterface.dismiss()
                onClick.onTryAgainClick()
            },
            negActionName = "cancel",
            negAction = {dialogInterface, i ->
                dialogInterface.dismiss()
            }
        )
    }

}