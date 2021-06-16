package com.example.adichallenge

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.adichallenge.adapters.ReviewsRecyclerViewAdapter
import com.example.adichallenge.models.Product
import com.example.adichallenge.network.CustomObserver
import com.example.adichallenge.viewmodel.ProductDetailsActivityViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*


class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var adapter: ReviewsRecyclerViewAdapter
    private lateinit var productActivityViewModel: ProductDetailsActivityViewModel

    companion object {
        val INTENT_EXTRA_PRODUCT = "PRODUCT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        val product = intent.getParcelableExtra<Product.ProductItem>(INTENT_EXTRA_PRODUCT)
        setProduct(product)
        productActivityViewModel = ViewModelProvider(this).get(ProductDetailsActivityViewModel::class.java)
        productActivityViewModel.reviews.observe(this, object : CustomObserver<List<Product.Review>?>() {
            override fun onChanged(reviews: List<Product.Review>?) {
                reviews?.let {
                    adapter = ReviewsRecyclerViewAdapter(this@ProductDetailsActivity, it.reversed())
                    recycler_view.adapter = adapter
                }
            }

            override fun onError(t: Throwable) {
            }
        })

        productActivityViewModel.reviewAdded.observe(this, object : CustomObserver<Boolean?>() {
            override fun onError(t: Throwable) {
            }

            override fun onChanged(t: Boolean?) {
                productActivityViewModel.getReviews(id = product.ID)
            }
        })

        productActivityViewModel.getReviews(product.ID)
        back_btn?.setOnClickListener {
            finish()
        }

        btn_add_review?.setOnClickListener {
            it?.let { addReview(id = product.ID!!) }

        }
    }

    fun setProduct(product: Product.ProductItem) {
        product_name.text = product.name
        product_description.text = product.description
        Picasso.get().load(product?.imageUrl).into(product_image)
        product_price.text = product?.price.formatCurrency()
    }


    fun addReview(id: String) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Product Review")
        alert.setMessage("Add your review and rating")
        val input1 = EditText(this)
        val numberPicker = NumberPicker(this)
        numberPicker.setMaxValue(5)
        numberPicker.setMinValue(1)

        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.VERTICAL
        ll.addView(input1)
        ll.addView(numberPicker)
        alert.setView(ll)

        alert.setPositiveButton(android.R.string.yes) { dialog, which ->
            val review = input1.getText().toString().trim()
            val rating = numberPicker.getValue()
            productActivityViewModel.addReview(id, review, rating)

        }
        alert.show()
    }
}