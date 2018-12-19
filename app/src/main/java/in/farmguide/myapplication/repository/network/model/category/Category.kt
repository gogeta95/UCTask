package `in`.farmguide.myapplication.repository.network.model.category

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categories")
    val categories: Categories
)
