package com.example.newsapp.ui.home.categoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentCategoryDetailsBinding
import com.example.newsapp.ui.home.categories.Category

class CategoryDetailsFragment :Fragment() {

    lateinit var viewBinding: FragmentCategoryDetailsBinding
    lateinit var category: Category

    companion object {
        fun getInstance(category: Category): CategoryDetailsFragment{
            // fragment variable is reference to CategoryDetailsFragment
            val fragment = CategoryDetailsFragment()
            fragment.category = category
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCategoryDetailsBinding.inflate(
            layoutInflater, container, false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }





}