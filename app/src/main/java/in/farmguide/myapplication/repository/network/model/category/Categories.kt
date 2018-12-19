package `in`.farmguide.myapplication.repository.network.model.category

import com.google.gson.annotations.SerializedName

data class Categories (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)
