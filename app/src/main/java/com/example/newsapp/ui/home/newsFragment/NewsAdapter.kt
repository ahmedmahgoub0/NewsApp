package com.example.newsapp.ui.home.newsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.api.newsResponse.ArticlesItem
import com.example.newsapp.databinding.ItemNewsBinding

class NewsAdapter(var newsList: List<ArticlesItem?>?=null)
    :RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = newsList!![position]
        holder.itemBinding.newsTitle.text = newsItem?.title
        holder.itemBinding.newsDesc.text = newsItem?.description
        Glide.with(holder.itemView)
            .load(newsItem?.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.logo)
            .into(holder.itemBinding.newsImage)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(newsItem!!)
        }
    }

    override fun getItemCount(): Int = newsList?.size?: 0
    fun bindNews(articles: List<ArticlesItem?>?) {
        this.newsList = articles
        notifyDataSetChanged()
    }

    class ViewHolder(val itemBinding: ItemNewsBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

    }

    var onItemClickListener: OnItemClickListener? = null
    fun interface OnItemClickListener{
        fun onItemClick(item: ArticlesItem)
    }

}