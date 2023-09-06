package com.example.newsapp.ui.home.categories

import com.example.newsapp.R

data class Category(
    val id: String,
    val name: String,
    val imageId: Int,
    val backgroundColorId: Int
) {
    companion object {
        fun getCategoriesList(): List<Category>{
            return listOf(
                Category(
                    id = "general",
                    name = "General",
                    imageId = R.drawable.logo,
                    backgroundColorId = R.color.dark_gery
                ),
                Category(
                    id = "sports",
                    name = "Sports",
                    imageId = R.drawable.sports,
                    backgroundColorId = R.color.red
                ),
                Category(
                    id = "entertainment",
                    name = "Entertainment",
                    imageId = R.drawable.environment,
                    backgroundColorId = R.color.babyBlue
                ),
                Category(
                    id = "health",
                    name = "Health",
                    imageId = R.drawable.health,
                    backgroundColorId = R.color.pink
                ),
                Category(
                    id = "business",
                    name = "Business",
                    imageId = R.drawable.busines,
                    backgroundColorId = R.color.orange
                ),
                Category(
                    id = "technology",
                    name = "Technology",
                    imageId = R.drawable.politics,
                    backgroundColorId = R.color.blue
                ),
                Category(
                    id = "science",
                    name = "Science",
                    imageId = R.drawable.science,
                    backgroundColorId = R.color.yellow
                ),
            )
        }
    }
}

