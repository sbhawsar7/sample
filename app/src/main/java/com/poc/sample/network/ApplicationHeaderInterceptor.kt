package com.poc.sample.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class ApplicationHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("platform", "android")
            .addHeader("locale", Locale.getDefault().language)
            .addHeader("timezone", TimeZone.getDefault().id)
            .addHeader("X-Api-Key", "7S1wt7HEN58KyU3F71pbD8kCWnJtX0jw8kauFEho")
            .addHeader("cid", "gwl18")
            .addHeader("api_version", "v2")
            .build()
        return chain.proceed(request)
    }
}