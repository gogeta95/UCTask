package `in`.farmguide.myapplication.ui.main

interface PaginatedCategoryCallBack {

    fun onLoadMoreRestaurantInCategory(nextPage: Int, categoryIndex: Int, currentSize: Int)
}