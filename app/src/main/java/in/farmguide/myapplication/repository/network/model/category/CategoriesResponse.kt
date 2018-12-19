package `in`.farmguide.myapplication.repository.network.model.category

import com.google.gson.annotations.SerializedName

data class CategoriesResponse (
    @SerializedName("categories")
    val categories: List<Category>
)
