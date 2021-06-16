package com.example.adichallenge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.adichallenge.adapters.ProductsRecylerViewAdapter
import com.example.adichallenge.models.Product
import com.example.adichallenge.network.CustomObserver
import kotlinx.android.synthetic.main.activity_products.*

import com.example.adichallenge.viewmodel.ProductActivityViewModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class ProductsActivity : AppCompatActivity(), ProductsRecylerViewAdapter.OnItemClickListener {

    private lateinit var adapter: ProductsRecylerViewAdapter
    private var filteredAdapter: ProductsRecylerViewAdapter? = null
    private var disposable: Disposable? = null
    private var emitter: ObservableEmitter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        val productActivityViewModel = ViewModelProvider(this).get(ProductActivityViewModel::class.java)
        productActivityViewModel.products.observe(this, object : CustomObserver<List<Product.ProductItem>?>() {
            override fun onChanged(products: List<Product.ProductItem>?) {
                products?.let {
                    adapter =
                        ProductsRecylerViewAdapter(this@ProductsActivity, it.toMutableList(), this@ProductsActivity)
                    recycler_view.adapter = adapter
                }
            }

            override fun onError(t: Throwable) {
            }
        })

        productActivityViewModel.getProducts()
        setupSearchView()


    }

    override fun onProductClickListener(product: Product.ProductItem?, position: Int) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra(ProductDetailsActivity.INTENT_EXTRA_PRODUCT, product)
        startActivity(intent)
    }

    fun setupSearchView() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (disposable?.isDisposed != true) {
                    disposable?.dispose()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (disposable == null || (disposable?.isDisposed == true)) {
                    startQueryListener()
                }
                newText?.let {
                    emitter?.onNext(it.toLowerCase().trim())
                }
                if (newText.isNullOrEmpty()) {
                    emitter?.onNext("")
                }
                return true
            }
        })
    }

    private fun startQueryListener() {
        disposable = Observable.create<String> { emitter = it }
            .debounce(750, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (!it.isNullOrEmpty()) {
                    var list = adapter?.getFilteredList(search = it)
                    filteredAdapter =
                        ProductsRecylerViewAdapter(this@ProductsActivity, list.toMutableList(), this@ProductsActivity)
                    filtered_recycler_view.adapter = filteredAdapter
                    recycler_view.visibility = View.GONE
                    filtered_recycler_view.visibility = View.VISIBLE
                } else {
                    recycler_view.visibility = View.VISIBLE
                    filtered_recycler_view.visibility = View.GONE
                    filteredAdapter =
                        null
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable?.isDisposed != true) {
            disposable?.dispose()
        }
    }

}