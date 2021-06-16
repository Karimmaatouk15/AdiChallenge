package com.example.adichallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adichallenge.R
import com.example.adichallenge.models.Product
import com.example.adichallenge.viewholders.ReviewHolder


class ReviewsRecyclerViewAdapter(
    private val context: Context,
    private val itemList: List<Product.Review?>
) : RecyclerView.Adapter<ReviewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_review, parent, false)
        return ReviewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.setReview(itemList[position])
    }
}


