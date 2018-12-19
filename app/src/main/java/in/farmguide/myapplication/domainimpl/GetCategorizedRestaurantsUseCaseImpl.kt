package `in`.farmguide.myapplication.domainimpl

import `in`.farmguide.myapplication.data.ui.CategorizedRestaurants
import `in`.farmguide.myapplication.domain.GetCategorizedRestaurantsUseCase
import `in`.farmguide.myapplication.repository.network.ApiRepository
import `in`.farmguide.myapplication.repository.network.model.category.Categories
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetCategorizedRestaurantsUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository
) : GetCategorizedRestaurantsUseCase {

    override fun getRestaurantsInCity(cityId: Long): Single<List<CategorizedRestaurants>> =
        apiRepository.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { it.categories }
            .flatMapObservable { Observable.fromIterable(it) }
            .observeOn(Schedulers.io())
            .flatMapMaybe({ getCategorizedRestaurants(it.categories, cityId, 0) }, true)
            .observeOn(Schedulers.computation())
            .toSortedList { o1, o2 -> o1.categoryId.compareTo(o2.categoryId) }

    override fun getCategorizedRestaurants(
        category: Categories,
        cityId: Long,
        start: Int
    ): Maybe<CategorizedRestaurants> {
        return apiRepository.getRestaurantInCategoryLocation(category.id, cityId, start, DEFAULT_PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map {
                CategorizedRestaurants(
                    category.name,
                    category.id,
                    it.restaurantWrapper?.map { it.restaurant }?.toMutableList() ?: mutableListOf()
                )
            }
            .doOnError { it.printStackTrace() }
            .onErrorResumeNext(
                Single.just(CategorizedRestaurants(category.name, category.id, mutableListOf()))
            )
            .filter { !it.restaurants.isEmpty() }
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}