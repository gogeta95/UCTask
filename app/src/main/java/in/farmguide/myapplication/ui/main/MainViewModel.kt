package `in`.farmguide.myapplication.ui.main

import `in`.farmguide.myapplication.R
import `in`.farmguide.myapplication.data.ui.CategorizedRestaurants
import `in`.farmguide.myapplication.data.ui.Resource
import `in`.farmguide.myapplication.domain.CityNotFoundException
import `in`.farmguide.myapplication.domain.GetCategorizedRestaurantsUseCase
import `in`.farmguide.myapplication.domain.SearchCityUseCase
import `in`.farmguide.myapplication.repository.network.model.category.Categories
import `in`.farmguide.myapplication.ui.base.BaseViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val getCategorizedRestaurantsUseCase: GetCategorizedRestaurantsUseCase,
    private val searchCityUseCase: SearchCityUseCase
) : BaseViewModel(), PaginatedCategoryCallBack {

    private val categoriesLiveData = MutableLiveData<Resource<List<CategorizedRestaurants>>>()
    fun getCategoriesObservable(): LiveData<Resource<List<CategorizedRestaurants>>> = categoriesLiveData

    private val cityLiveData = MutableLiveData<String>()
    fun getCityLiveData(): LiveData<String> = cityLiveData

    private var categories: List<CategorizedRestaurants>? = null
    private var currentCityId = DEFAULT_CITY_ID


    init {
        getRestaurants(currentCityId)
    }


    private fun getRestaurants(cityId: Long) {

        currentCityId = cityId

        categoriesLiveData.postValue(Resource.Loading())

        addDisposable(
            getCategorizedRestaurantsUseCase.getRestaurantsInCity(cityId)
                .subscribe({
                    updateAndPostCategories(it)
                }, {
                    it.printStackTrace()
                    categoriesLiveData.postValue(Resource.Error(R.string.error_msg))
                })
        )
    }

    private fun updateAndPostCategories(it: List<CategorizedRestaurants>) {
        categoriesLiveData.postValue(Resource.Success(it))
        categories = it
    }

    fun onCityUpdated(city: String) {
        addDisposable(
            searchCityUseCase.getMostrelevantCity(city)
                .applyLoader()
                .subscribe({
                    cityLiveData.postValue(it.name)
                    getRestaurants(it.id)

                }, {
                    it.printStackTrace()
                    if (it is CityNotFoundException)
                        postError(R.string.city_not_found)
                })
        )
    }

    override fun onLoadMoreRestaurantInCategory(nextPage: Int, categoryIndex: Int, currentSize: Int) {
        val categorizedRestaurants = categories?.get(categoryIndex)
        categorizedRestaurants?.let {

            addDisposable(
                getCategorizedRestaurantsUseCase.getCategorizedRestaurants(
                    Categories(it.categoryId, it.category),
                    currentCityId,
                    currentSize + 1
                )
                    .observeOn(Schedulers.computation())
                    .subscribe({
                        updaterestaurantsInCategory(categoryIndex, it)

                    }, {
                        it.printStackTrace()
                        postError(R.string.error_msg)
                    })
            )

        }
    }

    private fun updaterestaurantsInCategory(
        categoryIndex: Int,
        it: CategorizedRestaurants
    ) {
        val newCategories = categories?.map { it.copy() }?.toList()
        val newRestaurants =
            newCategories?.get(categoryIndex)?.restaurants?.map { it.copy() }?.toMutableList()
        newRestaurants?.addAll(it.restaurants)
        newCategories?.get(categoryIndex)?.restaurants = newRestaurants ?: mutableListOf()

        newCategories?.let {
            updateAndPostCategories(newCategories)
        }
    }

    companion object {
        //delhi is 1
        private const val DEFAULT_CITY_ID = 1L
    }
}