package com.example.adichallenge.network

import com.example.adichallenge.network.services.ProductService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Api {

    //region Http Client
    private val httpClient: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(defaultInterceptor)
    }


    private val defaultInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        val request = requestBuilder.build()
        chain.proceed(request)
    }


    //region services

    val productsService: ProductService by lazy {
        buildClient(interceptor = defaultInterceptor).create(ProductService::class.java)
    }

    val productsServiceReviews: ProductService by lazy {
        buildClient(interceptor = defaultInterceptor, portReviews = true).create(ProductService::class.java)
    }

    private fun buildClient(
        interceptor: Interceptor? = null,
        client: OkHttpClient.Builder = httpClient,
        portReviews: Boolean = false
    ): Retrofit {
        var baseUrl = ""
        if (portReviews) {
            baseUrl = "http://10.0.2.2:3002/"
        } else {
            baseUrl = "http://10.0.2.2:3001/"
        }

        interceptor?.let { client.addInterceptor(it) }
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(client.build())
            .build()
    }


}

fun Observable<Response<ResponseBody>>.toCustomObservable(
): Observable<Response<ResponseBody>> {
    return this.subscribeOn(Schedulers.io())
        .doOnError { error ->
            error.printStackTrace()
        }.map { response ->
            response
        }

}