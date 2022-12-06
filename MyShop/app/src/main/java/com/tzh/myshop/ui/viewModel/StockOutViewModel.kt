package com.tzh.myshop.ui.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzh.myshop.data.database.dao.ProductDao
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockOutViewModel @Inject constructor(
    val productRepository: ProductRepository,
    val productDao: ProductDao,
) : ViewModel() {
    var errorMessage = mutableStateOf<String?>(null)
    fun doneShowErrorMessage() {
        errorMessage.value = null
    }

    var productList = mutableStateListOf<Product>()

    fun addProduct(product: Product) {
        var isExist = productList.filter { it.id == product.id }.firstOrNull()
        if (isExist == null) {
            productList.add(product)
        }
    }

    fun removeProduct(product: Product) {
        productList.remove(product)
    }

    fun updateProduct(product: Product) {
        Log.e("PRoduct", product.toString())
        val mProduct = productList.firstOrNull { it.id == product.id }

        if (mProduct != null) {
            val index = productList.indexOf(mProduct)
            Log.e("ASD INDex", index.toString())
            productList[index] = product
        }
    }

    var filterList = mutableStateListOf<Product>()

    fun getData(productName: String = "") {
        viewModelScope.launch {
            filterList.clear()
            val list = if (productName.isNotEmpty()) {
                productDao.findByProductName("$productName%")
            } else {
                productDao.getAllProduct()
            }
            filterList.addAll(list.filter { it.currentQty != 0 })

            if (filterList.isEmpty()) {
                errorMessage.value = "Product not found"
            }

        }
    }

    var isLoading = mutableStateOf(false)
    var isSuccessSave = mutableStateOf(false)
    fun doneAction() {
        isSuccessSave.value = false
    }

    fun save(discount: Long, profit: Long) {
        viewModelScope.launch {
            isLoading.value = true
            delay(800)
            try {
                isLoading.value = false
                productRepository.saveStockOut(productList, discount, profit)
                isSuccessSave.value = true
            } catch (e: Exception) {
                isLoading.value = false
                errorMessage.value = e.message
            }
        }
    }

}