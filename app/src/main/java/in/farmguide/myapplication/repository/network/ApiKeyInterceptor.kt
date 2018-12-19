package `in`.farmguide.myapplication.repository.network

import `in`.farmguide.myapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(HEADER_API_KEY, BuildConfig.API_KEY)
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_API_KEY = "user-key"
    }

}