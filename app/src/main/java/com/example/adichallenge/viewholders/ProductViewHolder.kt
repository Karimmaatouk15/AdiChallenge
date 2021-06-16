package com.example.adichallenge.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adichallenge.R
import com.example.adichallenge.formatCurrency
import com.example.adichallenge.models.Product
import com.squareup.picasso.Picasso

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val product_name = itemView.findViewById<TextView>(R.id.product_name)
    private val product_description = itemView.findViewById<TextView>(R.id.product_description)
    private val product_price = itemView.findViewById<TextView>(R.id.product_price)
    private val product_image = itemView.findViewById<ImageView>(R.id.product_image)


    fun setProduct(product: Product.ProductItem?) {
        product_name.text = product?.name
        product_description.text = product?.description
        product_price.text = product?.price.formatCurrency()
        Picasso.get().load(product?.imageUrl).into(product_image)


    }
}