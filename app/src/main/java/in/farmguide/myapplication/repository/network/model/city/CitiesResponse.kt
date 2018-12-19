package `in`.farmguide.myapplication.repository.network.model.city

import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("has_more")
    val hasMore: Long?,
    @SerializedName("has_total")
    val hasTotal: Long?,
    @SerializedName("location_suggestions")
    val locationSuggestions: List<LocationSuggestion>?,
    @SerializedName("status")
    val status: String?
)
