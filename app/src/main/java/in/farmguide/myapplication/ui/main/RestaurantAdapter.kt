package `in`.farmguide.myapplication.ui.main

import `in`.farmguide.myapplication.R
import `in`.farmguide.myapplication.extensions.loadImage
import `in`.farmguide.myapplication.repository.network.model.restaurant.Restaurant
import android.graphics.Color
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.restaurant_item_base.view.*
import timber.log.Timber

class RestaurantAdapter(private val layoutInflater: LayoutInflater, private val type: Int) :
    RecyclerView.Adapter<RestaurantAdapter.BaseViewHolder>() {

    private var isLoading = true

    var restaurants: List<Restaurant> = emptyList()
        set(value) {

            // when no new data.
            if (field.size==value.size){
                isLoading = false
            }

            val diffResult = DiffUtil.calculateDiff(RestaurantDiffUtilCallback(field, value, isLoading))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == VIEW_TYPE_CONTENT)
            RestaurantViewHolder(
                layoutInflater.inflate(
                    if (type == CategoryAdapter.VIEW_TYPE_LIST) R.layout.restaurant_item_normal
                    else R.layout.restaurant_item_card,
                    parent,
                    false
                )
            )
        else
            LoadingViewHolder(layoutInflater.inflate(
                R.layout.loader,
                parent,
                false
            ))
    }

    override fun getItemCount() = restaurants.size + if (isLoading) 1 else 0

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is RestaurantViewHolder)
            holder.bind(restaurants[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position >= restaurants.size)
            VIEW_TYPE_LOADER
        else VIEW_TYPE_CONTENT
    }

    private class RestaurantDiffUtilCallback(
        private val oldList: List<Restaurant>,
        private val newList: List<Restaurant>,
        private val isLoading: Boolean
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size + if (isLoading) 1 else 0

        override fun getNewListSize() = newList.size + if (isLoading) 1 else 0

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return if (!isLoading)
                oldList[oldItemPosition].id == newList[newItemPosition].id
            else {
                if (oldItemPosition == oldList.size  || newItemPosition == newList.size) {
                    oldItemPosition == newItemPosition
                } else oldList[oldItemPosition].id == newList[newItemPosition].id
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return if (!isLoading)
                oldList[oldItemPosition] == newList[newItemPosition]
            else {
                if (oldItemPosition == oldList.size || newItemPosition == newList.size) {
                    oldItemPosition == newItemPosition
                } else oldList[oldItemPosition] == newList[newItemPosition]
            }
        }

    }


    inner class RestaurantViewHolder(itemView: View) : BaseViewHolder(itemView) {

        fun bind(restaurant: Restaurant) {
            with(itemView) {
                rest_image.loadImage(restaurant.thumb)
                tv_rest_name.text = restaurant.name
                tv_cuisines.text = restaurant.cuisines
                tv_rating.text = restaurant.userRating?.aggregateRating
                val ratingColor = restaurant.userRating?.ratingColor

                tv_rating.setBackgroundColor(
                    if (ratingColor.isNullOrEmpty())
                        Color.TRANSPARENT
                    else
                        Color.parseColor("#$ratingColor")
                )

            }
        }

    }

    inner class LoadingViewHolder(itemView: View) : BaseViewHolder(itemView)

    open inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        private const val VIEW_TYPE_LOADER = 1
        private const val VIEW_TYPE_CONTENT = 2
    }
}