package `in`.farmguide.myapplication.domainimpl

import `in`.farmguide.myapplication.domain.CityNotFoundException
import `in`.farmguide.myapplication.domain.SearchCityUseCase
import `in`.farmguide.myapplication.repository.network.ApiRepository
import `in`.farmguide.myapplication.repository.network.model.city.LocationSuggestion
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchCityUseCaseImpl @Inject constructor(private val apiRepository: ApiRepository) : SearchCityUseCase {
    override fun getMostrelevantCity(query: String): Single<LocationSuggestion> =
        apiRepository.getCities(query)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .doOnSuccess {
                if (it.locationSuggestions?.size == 0)
                    throw CityNotFoundException()
            }
            .map { it.locationSuggestions?.get(0) }
}