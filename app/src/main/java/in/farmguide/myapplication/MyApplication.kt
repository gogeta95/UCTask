package `in`.farmguide.myapplication

import `in`.farmguide.myapplication.dependency.component.AppComponent
import `in`.farmguide.myapplication.dependency.component.DaggerAppComponent
import android.app.Activity
import android.app.Application
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this))
            return

        LeakCanary.install(this)

        appComponent =
                DaggerAppComponent
                    .builder()
                    .app(this)
                    .build()

        appComponent.inject(this)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    override fun activityInjector() = dispatchingAndroidInjector
}