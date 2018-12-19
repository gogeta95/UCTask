package `in`.farmguide.myapplication.dependency.builder

import `in`.farmguide.myapplication.ui.main.MainActivity
import `in`.farmguide.myapplication.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun bindMainActivity(): MainActivity
}