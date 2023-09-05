package com.example.newsapp.api

import com.example.newsapp.api.sourcesResponse.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("apiKey")
        apiKey: String = ApiConstants.API_KEY
    ) :Call<SourcesResponse>
}