package com.example.newsapp.ui.home.newsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.ApiConstants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.newsResponse.News
import com.example.newsapp.api.newsResponse.NewsResponse
import com.example.newsapp.api.sourcesResponse.model.Source
import com.example.newsapp.ui.ViewError
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel :ViewModel() {

    val progressBarLiveData = MutableLiveData<Boolean>()
    val articlesLiveData = MutableLiveData<List<News?>?>()
    val viewErrorLiveData = MutableLiveData<ViewError>()
    fun getNews(source: Source) {
        progressBarLiveData.postValue(true)
        ApiManager
            .getApis()
            .getNews(ApiConstants.API_KEY, source.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressBarLiveData.postValue(false)
                    if (response.isSuccessful) {
                        articlesLiveData.postValue(response.body()?.articles)
                        return
                    }
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.string(), NewsResponse::class.java
                    )
                    viewErrorLiveData.postValue(
                        ViewError(errorResponse.message?:"something went wrong"
                        ) { getNews(source) }
                    )
                }
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBarLiveData.postValue(false)
                    viewErrorLiveData.postValue(
                        ViewError(t.localizedMessage?:"something went wrong"
                        ) {getNews(source) }
                    )
                }
            })
    }

}