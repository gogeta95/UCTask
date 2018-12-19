package `in`.farmguide.myapplication.ui.main

import `in`.farmguide.myapplication.R
import `in`.farmguide.myapplication.data.ui.CategorizedRestaurants
import `in`.farmguide.myapplication.ui.util.PaginationScrollListener
import android.support.v7.util.DiffUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.category_item.view.*
import timber.log.Timber
import javax.inject.Inject

class CategoryAdapter @Inject constructor(private val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var scrollXState: IntArray

    var categories: List<CategorizedRestaurants> = emptyList()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(CategoryDiffUtilCallback(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
            scrollXState = IntArray(field.size)
        }

    var paginatedCategoryCallBack: PaginatedCategoryCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder(layoutInflater.inflate(R.layout.category_item, parent, false), viewType)


    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(categories[position])

    override fun getItemViewType(position: Int) =
        when (position % 3) {
            0 -> VIEW_TYPE_CARDS
            1 -> VIEW_TYPE_GRID
            else -> VIEW_TYPE_LIST
        }

    override fun onViewRecycled(holder: CategoryViewHolder) {
        Timber.d("onViewRecycled: ${holder.itemView.recycler_view.computeHorizontalScrollOffset()}")
        scrollXState[holder.adapterPosition] = holder.itemView.recycler_view.computeHorizontalScrollOffset()
        super.onViewRecycled(holder)
    }

    inner class CategoryViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        private val restaurantAdapter: RestaurantAdapter = RestaurantAdapter(layoutInflater, viewType)

        init {

            with(itemView) {
                val layoutManager =
                    if (viewType == VIEW_TYPE_LIST || viewType == VIEW_TYPE_CARDS)
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    else
                        GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                recycler_view.layoutManager = layoutManager
                recycler_view.adapter = restaurantAdapter

                recycler_view.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int) {
                        Timber.d("page: $page , totalItemsCount: $totalItemsCount")

                        paginatedCategoryCallBack?.onLoadMoreRestaurantInCategory(
                            page,
                            adapterPosition,
                            totalItemsCount
                        )
                    }
                })
            }
        }

        fun bind(category: CategorizedRestaurants) =
            with(itemView) {
                tv_category.text = category.category
                restaurantAdapter.restaurants = category.restaurants

                updateScrollX(recycler_view)

                Timber.d("category: ${category.category}, ${scrollXState[adapterPosition]}")
            }

        private fun updateScrollX(recycler_view: RecyclerView) {
            recycler_view.post {
                recycler_view.scrollX = scrollXState[adapterPosition]
            }
        }

    }

    private class CategoryDiffUtilCallback(
        private val oldList: List<CategorizedRestaurants>,
        private val newList: List<CategorizedRestaurants>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].categoryId == newList[newItemPosition].categoryId

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    }

    companion object {
        const val VIEW_TYPE_GRID = 1
        const val VIEW_TYPE_LIST = 2
        const val VIEW_TYPE_CARDS = 3
    }
}