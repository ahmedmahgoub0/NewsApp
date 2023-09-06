package com.example.newsapp.ui.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentCategoriesBinding

class CategoriesFragment :Fragment() {

    lateinit var viewBinding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCategoriesBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        val adapter = CategoryAdapter(Category.getCategoriesList())
        viewBinding.categoriesRv.adapter = adapter
        adapter.onItemClickListener =
            CategoryAdapter.OnItemClickListener { position, item ->
                onCategoryClickListener?.onCategoryClick(item)
            }
    }

    var onCategoryClickListener: OnCategoryClickListener? = null
    fun interface OnCategoryClickListener{
        fun onCategoryClick(category: Category)
    }

}