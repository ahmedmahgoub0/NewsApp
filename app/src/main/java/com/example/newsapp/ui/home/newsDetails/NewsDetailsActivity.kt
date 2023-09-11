package com.example.newsapp.ui.home.newsDetails

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.api.newsResponse.ArticlesItem
import com.example.newsapp.databinding.ActivityNewsDetailsBinding
import com.example.newsapp.ui.Constants


class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityNewsDetailsBinding
    private var newsItem: ArticlesItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initParams()
        initViews()
    }

    private fun initViews(){
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        Glide.with(this)
            .load(newsItem?.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.logo)
            .into(viewBinding.newsImage)
        viewBinding.newsTitle.text = newsItem?.title
        viewBinding.newsDesc.text = newsItem?.description
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun initParams() {
        newsItem = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(Constants.NEWS_ITEM_KEY, ArticlesItem::class.java)
        else
            intent.getParcelableExtra(Constants.NEWS_ITEM_KEY) as ArticlesItem?
    }


}