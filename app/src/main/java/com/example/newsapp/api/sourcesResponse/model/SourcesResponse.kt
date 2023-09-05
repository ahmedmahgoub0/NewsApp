package com.example.newsapp.api.sourcesResponse.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SourcesResponse(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("sources")
	val sources: List<SourcesItem?>? = null
) : Parcelable