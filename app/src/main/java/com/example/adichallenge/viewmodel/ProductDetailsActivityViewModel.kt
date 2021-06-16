package com.example.adichallenge.viewmodel

import androidx.lifecycle.ViewModel
import com.example.adichallenge.models.Product
import com.example.adichallenge.network.CustomLiveData
import com.example.adichallenge.viewmodel.observables.ProductObservable

class ProductDetailsActivityViewModel : ViewModel() {

    val reviews: CustomLiveData<List<Product.Review>> = CustomLiveData()
    val reviewAdded: CustomLiveData<Boolean> = CustomLiveData()
    fun getReviews(id: String?) {
        ProductObservable.getReviews(id)
            .subscribe({ response ->
                reviews.postValue(response)
            }, { error ->
                reviews.error = error
                error.printStackTrace()
            })
    }


    fun addReview(id: String, review: String, rating: Int) {
        ProductObservable.addReview(id, review, rating)
            .subscribe({ response ->
                reviewAdded.postValue(true)
            }, { error ->
                reviewAdded.error = error
                error.printStackTrace()
            })
    }
}