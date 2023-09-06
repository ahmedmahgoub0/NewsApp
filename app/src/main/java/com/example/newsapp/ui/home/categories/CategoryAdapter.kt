package com.example.newsapp.ui.home.categories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemCategoryBinding

class CategoryAdapter(val categoriesList :List<Category>)
    :RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = categoriesList[position]
        holder.bind(category)
        holder.itemBinding.itemContainer.setOnClickListener{
            onItemClickListener?.onItemClick(position, category)
        }
    }

    override fun getItemCount(): Int = categoriesList.size?:0


    class ViewHolder(val itemBinding: ItemCategoryBinding)
        :RecyclerView.ViewHolder(itemBinding.root){
        fun bind(category: Category){
            itemBinding.image.setImageResource(category.imageId)
            itemBinding.textView.text = category.name
            itemBinding.itemContainer.setBackgroundColor(
                itemBinding.itemContainer.resources.getColor(category.backgroundColorId, null)
            )
        }
    }

    var onItemClickListener: OnItemClickListener? = null
    fun interface OnItemClickListener{
        fun onItemClick(position :Int, item: Category)
    }

}