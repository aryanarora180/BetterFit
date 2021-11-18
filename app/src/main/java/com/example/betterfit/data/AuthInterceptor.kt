package com.example.betterfit.data

import android.content.Context
import com.example.betterfit.helper.DataStoreUtils
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor : Interceptor {

    @Inject
    lateinit var dataStoreUtils: DataStoreUtils

    override fun intercept(chain: Interceptor.Chain): Response {
        // Build the request with the header as the auth token, if it exists
        val mainRequest = chain.request().newBuilder()
        val authToken = runBlocking { dataStoreUtils.getAuthToken() }
        authToken?.let {
            mainRequest.addHeader("Authorization", it)
        }

        return chain.proceed(mainRequest.build())
    }
}