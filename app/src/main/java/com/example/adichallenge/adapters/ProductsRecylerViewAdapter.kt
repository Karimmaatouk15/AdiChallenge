package com.example.adichallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adichallenge.R
import com.example.adichallenge.models.Product
import com.example.adichallenge.viewholders.ProductViewHolder

class ProductsRecylerViewAdapter(
    private val context: Context,
    private var itemList: MutableList<Product.ProductItem>,
    private val listener: OnItemClickListener?
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_product_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.setProduct(itemList[position])
        holder.itemView.setOnClickListener {
            listener?.onProductClickListener(itemList[position], position)
        }
    }

    fun getFilteredList(search: String): List<Product.ProductItem> {
        return itemList.toList().filter {
            (it.name?.contains(search, ignoreCase = true) == true) || (it.description?.contains(
                search,
                ignoreCase = true
            ) == true)
        }
    }


    interface OnItemClickListener {
        fun onProductClickListener(product: Product.ProductItem?, position: Int)
    }

}