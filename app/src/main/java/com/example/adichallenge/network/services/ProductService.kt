package com.example.adichallenge.network.services

import com.example.adichallenge.models.Product
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import io.reactivex.Observable
import retrofit2.http.Path

interface ProductService {


    @GET("product")
    fun getProducts(): Observable<Response<ResponseBody>>


    @GET("reviews/{id}")
    fun getReviews(@Path("id") id: String?): Observable<Response<ResponseBody>>

    @POST("reviews/{id}")
    fun addReview(@Path("id") id: String, @Body request: Product.AddReviewRequest): Observable<Response<ResponseBody>>
}