package com.example.newsapp.ui.home.newsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.api.newsResponse.News
import com.example.newsapp.databinding.ItemNewsBinding

class NewsAdapter(var items: List<News?>?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    class ViewHolder(val viewBinding: ItemNewsBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position);
        holder.viewBinding.newsTitle.text = item?.title
        holder.viewBinding.newsDesc.text = item?.description

        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .placeholder(R.drawable.logo)
            .into(holder.viewBinding.newsImage)
    }

    override fun getItemCount(): Int = items?.size ?: 0
    fun changeData(articles: List<News?>?) {
        items = articles;
        notifyDataSetChanged()
    }
}