package com.dinesh.kotlinstructure.router

import com.dinesh.kotlinstructure.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetroClient {

    val BASE_URL = ApiConstant.URL.BASE

    fun getClient(url: String = "", timeOut: Long = 60, timeUnit: TimeUnit = TimeUnit.SECONDS): Retrofit {

        val clientBuilder = getBuilder(timeOut, timeUnit)

        setInterceptor(clientBuilder)

        return Retrofit.Builder()
                .baseUrl(url.takeIf { it.isEmpty() }?.run { BASE_URL } ?: url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build()
    }

    private fun getBuilder(timeout: Long, timeUnit: TimeUnit): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .readTimeout(timeout, timeUnit)
                .connectTimeout(timeout, timeUnit)
                .writeTimeout(timeout, timeUnit)
    }

    fun getProgressClient(url: String = "", progressListener: ProgressResponseBody.Listener): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
                .addNetworkInterceptor(Interceptor { chain ->
                    var originalResponse: Response? = null
                    try {
                        originalResponse = chain.proceed(chain.request())
                        return@Interceptor originalResponse!!.newBuilder()
                                .body(ProgressResponseBody(originalResponse.body(), progressListener))
                                .build()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    null
                }).readTimeout(30, TimeUnit.MINUTES)
                .connectTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)

        setInterceptor(clientBuilder)

        return Retrofit.Builder()
                .baseUrl(url.takeIf { it.isEmpty() }.run { BASE_URL })
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    fun setInterceptor(clientBuilder: OkHttpClient.Builder) {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(interceptor)
        }
    }
}
