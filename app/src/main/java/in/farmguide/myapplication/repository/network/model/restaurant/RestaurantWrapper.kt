package `in`.farmguide.myapplication.repository.network.model.restaurant

import com.google.gson.annotations.SerializedName

data class RestaurantWrapper(
    @SerializedName("restaurant")
    val restaurant: Restaurant
)