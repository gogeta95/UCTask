package `in`.farmguide.myapplication.dependency.module

import `in`.farmguide.myapplication.domain.GetCategorizedRestaurantsUseCase
import `in`.farmguide.myapplication.domain.SearchCityUseCase
import `in`.farmguide.myapplication.domainimpl.GetCategorizedRestaurantsUseCaseImpl
import `in`.farmguide.myapplication.domainimpl.SearchCityUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideGetPostsUseCase(getPostsUseCaseImpl: GetCategorizedRestaurantsUseCaseImpl): GetCategorizedRestaurantsUseCase =
        getPostsUseCaseImpl

    @Provides
    @Singleton
    fun provideSearchCityUseCase(searchCityUseCaseImpl: SearchCityUseCaseImpl): SearchCityUseCase =
        searchCityUseCaseImpl

}