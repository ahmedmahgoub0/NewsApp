package com.example.newsapp.ui.home.newsDetails

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.api.newsResponse.News
import com.example.newsapp.databinding.ActivityNewsDetailsBinding
import com.example.newsapp.ui.Constants


class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityNewsDetailsBinding
    private var newsItem: News? = null
    private var category: String? = "News App"

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
        viewBinding.toolbarTv.text = category

        Glide.with(this)
            .load(newsItem?.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.logo)
            .into(viewBinding.newsImage)
        viewBinding.newsSource.text = newsItem?.source?.name
        viewBinding.newsTitle.text = newsItem?.title
        viewBinding.newsTime.text = newsItem?.publishedAt
        viewBinding.newsContent.text = newsItem?.content
    }

    private fun initParams() {
        newsItem = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(Constants.NEWS_ITEM_KEY, News::class.java)
        else
            intent.getParcelableExtra(Constants.NEWS_ITEM_KEY) as News?
        category = intent.getStringExtra(Constants.CATEGORY_NAME)
    }

}