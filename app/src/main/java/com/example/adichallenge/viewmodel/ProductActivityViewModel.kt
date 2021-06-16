package com.example.adichallenge.viewmodel

import androidx.lifecycle.ViewModel
import com.example.adichallenge.models.Product
import com.example.adichallenge.network.CustomLiveData
import com.example.adichallenge.viewmodel.observables.ProductObservable


class ProductActivityViewModel : ViewModel() {

    val products: CustomLiveData<List<Product.ProductItem>> = CustomLiveData()

    fun getProducts() {
        ProductObservable.getProducts()
            .subscribe({ response ->
                products.postValue(response)
            }, { error ->
                products.error = error
                error.printStackTrace()
            })
    }


}