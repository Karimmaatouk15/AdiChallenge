package com.example.adichallenge.viewmodel.observables

import com.example.adichallenge.models.Product
import com.example.adichallenge.network.Api
import com.example.adichallenge.network.toCustomObservable
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

class ProductObservable {
    companion object {
        fun getProducts(): Observable<List<Product.ProductItem>> {
            return Api.productsService.getProducts().toCustomObservable()
                .map { response ->
                    val turnsType = object : TypeToken<List<Product.ProductItem>>() {}.type
                    val body = response.body()?.string()
                    if (!body.isNullOrBlank()) {
                        val gson = GsonBuilder().create()
                        return@map gson.fromJson<List<Product.ProductItem>>(body, turnsType)
                    } else if (body!!.isEmpty()) {
                        return@map listOf<Product.ProductItem>()
                    }
                    throw Throwable("Invalid Response")
                }
        }

        fun getReviews(id: String?): Observable<List<Product.Review>> {
            return Api.productsServiceReviews.getReviews(id).toCustomObservable()
                .map { response ->
                    val turnsType = object : TypeToken<List<Product.Review>>() {}.type
                    val body = response.body()?.string()
                    if (!body.isNullOrBlank()) {
                        val gson = GsonBuilder().create()
                        return@map gson.fromJson<List<Product.Review>>(body, turnsType)
                    } else if (body!!.isEmpty()) {
                        return@map listOf<Product.Review>()
                    }
                    throw Throwable("Invalid Response")
                }
        }

        fun addReview(id: String, review: String, rating: Int): Observable<Response<ResponseBody>> {
            val request = Product.AddReviewRequest(productId = id, rating = rating, text = review)
            return Api.productsServiceReviews.addReview(id, request).toCustomObservable()
        }

    }
}