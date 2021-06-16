package com.example.adichallenge.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


class Product {
    data class AddReviewRequest(
        @SerializedName("productId") var productId: String,
        @SerializedName("rating") var rating: Int,
        @SerializedName("text") var text: String
    )

    @Parcelize
    data class ProductItem(
        @SerializedName("id") var ID: String?,
        @SerializedName("name") var name: String?,
        @SerializedName("description") var description: String?,
        @SerializedName("imgUrl") val imageUrl: String?,
        @SerializedName("price") val price: Float?
    ) : Parcelable


    data class Review(
        @SerializedName("productId") var productId: String?,
        @SerializedName("rating") var rating: Int?,
        @SerializedName("locale") var locale: String?,
        @SerializedName("text") var text: String?
    )

}
