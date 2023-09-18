package com.example.newsapp.ui.home.categoryDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.ApiConstants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.sourcesResponse.model.Source
import com.example.newsapp.api.sourcesResponse.model.SourcesResponse
import com.example.newsapp.ui.ViewError
import com.example.newsapp.ui.home.categories.Category
import com.example.newsapp.ui.home.newsFragment.handleError
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesDetailsViewModel: ViewModel() {

    val progressBarLiveData = MutableLiveData<Boolean>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val viewErrorLiveData = MutableLiveData<ViewError>()
    fun loadNewsSources(category: Category) {
        progressBarLiveData.postValue(true)
        ApiManager
            .getApis()
            .getSources(ApiConstants.API_KEY, category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBarLiveData.postValue(false)
                    if (response.isSuccessful) {
                        sourcesLiveData.postValue(response.body()?.sources)
                    } else {
                        val gson = Gson()
                        val errorResponse =
                            gson.fromJson(
                                response.errorBody()?.string(),
                                SourcesResponse::class.java
                            );
                        viewErrorLiveData.postValue(
                            ViewError(errorResponse.message?:"something went wrong"){
                                loadNewsSources(category)
                            }
                        )
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressBarLiveData.postValue(false)
                    viewErrorLiveData.postValue(
                        ViewError(t.localizedMessage?:"something went wrong"){
                            loadNewsSources(category)
                        }
                    )
                }
            })
    }

}