package `in`.farmguide.myapplication.repository.network.model.restaurant

import com.google.gson.annotations.SerializedName

data class Restaurant (
    @SerializedName("average_cost_for_two")
    val averageCostForTwo: Long?,
    @SerializedName("book_again_url")
    val bookAgainUrl: String?,
    @SerializedName("book_form_web_view_url")
    val bookFormWebViewUrl: String?,
    @SerializedName("book_url")
    val bookUrl: String?,
    @SerializedName("cuisines")
    val cuisines: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("deeplink")
    val deeplink: String?,
    @SerializedName("events_url")
    val eventsUrl: String?,
    @SerializedName("featured_image")
    val featuredImage: String?,
    @SerializedName("has_online_delivery")
    val hasOnlineDelivery: Long?,
    @SerializedName("has_table_booking")
    val hasTableBooking: Long?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("include_bogo_offers")
    val includeBogoOffers: Boolean?,
    @SerializedName("is_book_form_web_view")
    val isBookFormWebView: Long?,
    @SerializedName("is_delivering_now")
    val isDeliveringNow: Long?,
    @SerializedName("is_table_reservation_supported")
    val isTableReservationSupported: Long?,
    @SerializedName("is_zomato_book_res")
    val isZomatoBookRes: Long?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("medio_provider")
    val medioProvider: Long?,
    @SerializedName("menu_url")
    val menuUrl: String?,
    @SerializedName("mezzo_provider")
    val mezzoProvider: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("opentable_support")
    val opentableSupport: Long?,
    @SerializedName("photos_url")
    val photosUrl: String?,
    @SerializedName("price_range")
    val priceRange: Long?,
    @SerializedName("switch_to_order_menu")
    val switchToOrderMenu: Long?,
    @SerializedName("thumb")
    val thumb: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("user_rating")
    val userRating: UserRating?
)
