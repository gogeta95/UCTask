package `in`.farmguide.myapplication.data.ui

import `in`.farmguide.myapplication.repository.network.model.restaurant.Restaurant

data class CategorizedRestaurants(
    val category: String,
    val categoryId: Long,
    var restaurants: MutableList<Restaurant>
)