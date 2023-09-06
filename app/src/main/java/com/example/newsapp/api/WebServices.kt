package com.example.newsapp.api

import com.example.newsapp.api.newsResponse.NewsResponse
import com.example.newsapp.api.sourcesResponse.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("apiKey") apiKey: String = ApiConstants.API_KEY,
        @Query("category") category: String
    ) :Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(
        @Query("apiKey") apiKey: String = ApiConstants.API_KEY,
        @Query("sources") sources: String
    ): Call<NewsResponse>

}