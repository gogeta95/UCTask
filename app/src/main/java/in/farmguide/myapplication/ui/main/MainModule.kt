package `in`.farmguide.myapplication.ui.main

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideViewModel(mainActivity: MainActivity, mainViewModelFactory: MainViewModelFactory) =
            ViewModelProviders.of(mainActivity, mainViewModelFactory).get(MainViewModel::class.java)

    @Provides
    fun provideCategoryAdapter(mainActivity: MainActivity) =
            CategoryAdapter(mainActivity.layoutInflater)

}