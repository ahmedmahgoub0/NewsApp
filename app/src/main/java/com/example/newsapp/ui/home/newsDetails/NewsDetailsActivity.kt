package com.example.newsapp.ui.home.newsDetails

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.api.newsResponse.News
import com.example.newsapp.databinding.ActivityNewsDetailsBinding
import com.example.newsapp.ui.Constants
import com.example.newsapp.ui.home.categories.Category


class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityNewsDetailsBinding
    private var newsItem: News? = null
    private var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initParams()
        initViews()
    }

    private fun initViews(){
        viewBinding.arrowBack.setOnClickListener {
            finish()
        }
        viewBinding.toolbarTv.text = category?.name ;

        Glide.with(this)
            .load(newsItem?.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.logo)
            .into(viewBinding.newsImage)
        viewBinding.newsSource.text = newsItem?.source?.name
        viewBinding.newsTitle.text = newsItem?.title
        viewBinding.newsTime.text = newsItem?.publishedAt
        viewBinding.newsDescContent.text = newsItem?.description
        viewBinding.newsContentInfo.text = newsItem?.content
    }

    private fun initParams() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            newsItem = intent.getParcelableExtra(Constants.NEWS_ITEM_KEY, News::class.java)
            category = intent.getParcelableExtra(Constants.CATEGORY, Category::class.java)
        }else {
            newsItem = intent.getParcelableExtra(Constants.NEWS_ITEM_KEY) as News?
            category = intent.getParcelableExtra(Constants.CATEGORY) as Category?
        }


    }

}