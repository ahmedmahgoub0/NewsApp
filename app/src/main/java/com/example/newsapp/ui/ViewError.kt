package com.example.newsapp.ui

import com.example.newsapp.ui.home.newsFragment.OnTryAgainClickListener

data class ViewError(
    val message: String? = null,
    val onTryAgainClickListener: OnTryAgainClickListener? = null
)