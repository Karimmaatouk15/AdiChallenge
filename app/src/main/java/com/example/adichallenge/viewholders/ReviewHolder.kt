package com.example.adichallenge.viewholders

import android.view.View

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adichallenge.R
import com.example.adichallenge.models.Product

class ReviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val review = itemView.findViewById<TextView>(R.id.review)
    private val rating = itemView.findViewById<TextView>(R.id.rating)

    fun setReview(reviewItem: Product.Review?) {
        review?.text = reviewItem?.text
        rating?.text = reviewItem?.rating.toString()
    }
}