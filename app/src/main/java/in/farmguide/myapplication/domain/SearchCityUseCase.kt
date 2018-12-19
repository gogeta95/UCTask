package `in`.farmguide.myapplication.domain

import `in`.farmguide.myapplication.repository.network.model.city.LocationSuggestion
import io.reactivex.Single

interface SearchCityUseCase {

    fun getMostrelevantCity(query: String): Single<LocationSuggestion>

}