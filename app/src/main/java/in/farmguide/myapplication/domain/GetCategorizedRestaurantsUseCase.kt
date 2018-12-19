package `in`.farmguide.myapplication.domain

import `in`.farmguide.myapplication.data.ui.CategorizedRestaurants
import `in`.farmguide.myapplication.repository.network.model.category.Categories
import io.reactivex.Maybe
import io.reactivex.Single

interface GetCategorizedRestaurantsUseCase {

    fun getRestaurantsInCity(cityId: Long): Single<List<CategorizedRestaurants>>

    fun getCategorizedRestaurants(category: Categories, cityId: Long, start: Int): Maybe<CategorizedRestaurants>
}