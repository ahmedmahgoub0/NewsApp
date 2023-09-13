package com.example.newsapp.ui.home.categories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemCategoryBinding

class CategoryAdapter(val items: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.textView.text = items[position].name
        holder.itemBinding.itemCard.setCardBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                items[position].backgroundColorId
            )
        )
        holder.itemBinding.image.setImageResource(items[position].imageId)
        onItemClickListener?.let { clickListener ->
            holder.itemBinding.itemCard.setOnClickListener {
                clickListener.onItemClick(position, items[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = items.size
    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(pos: Int, item: Category)
    }

    class ViewHolder(val itemBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}