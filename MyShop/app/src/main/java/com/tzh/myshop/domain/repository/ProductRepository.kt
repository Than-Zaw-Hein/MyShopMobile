package com.tzh.myshop.domain.repository

import android.util.Log
import com.tzh.myshop.common.ulti.Constant
import com.tzh.myshop.data.database.dao.ProductDao
import com.tzh.myshop.data.database.dao.TransactionDetailDao
import com.tzh.myshop.data.database.dao.TransactionHeaderDao
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.data.database.entity.TransactionDetail
import com.tzh.myshop.data.database.entity.TransactionHeader
import kotlinx.coroutines.delay
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val transactionHeaderDao: TransactionHeaderDao,
    private val transactionDetailDao: TransactionDetailDao,
) {


    suspend fun getProduct(productName: String): List<Product> {
        return if (productName.isNotEmpty()) {
            productDao.findByProductName("$productName%")
        } else {
            productDao.getAllProduct()
        }
    }

    suspend fun createProduct(product: Product) {
        val productId = productDao.insert(product)

        Log.e("ASD", Constant.TransactionType.STOCKIN.getId().toString())
        val header = TransactionHeader(
            typeName = Constant.TransactionType.STOCKIN.getTypeName(), createDate = Constant.getCurrentTime(), totalProfit = 0,
        )

        val transactionHeaderId = transactionHeaderDao.insert(header)

        val transactionDetail = TransactionDetail(
            transactionHeaderId = transactionHeaderId,
            createDate = header.createDate,
            productId = productId,
            productName = product.productName,
            originalPrice = product.originalPrice,
            sellingPrice = product.sellingPrice,
            profit = product.profit,
            qty = product.currentQty,
        )
        transactionDetailDao.insert(transactionDetail)

    }

    suspend fun updateProduct(product: Product) {
        Log.e("PRODUCT", product.toString())
        var productId = productDao.update(product)
        val header = TransactionHeader(
            typeName = Constant.TransactionType.UPDATEITEM.getTypeName(), createDate = Constant.getCurrentTime(), totalProfit = 0
        )

        val transactionHeaderId = transactionHeaderDao.insert(header)

        val transactionDetail = TransactionDetail(
            transactionHeaderId = transactionHeaderId,
            createDate = header.createDate,
            productId = product.id!!,
            qty = product.currentQty,
            productName = product.productName,
            originalPrice = product.originalPrice,
            sellingPrice = product.sellingPrice,
            profit = product.profit,
        )
        transactionDetailDao.insert(transactionDetail)

    }

    suspend fun deleteProduct(product: Product) {
        productDao.delete(product)
        Log.e("ASD", Constant.TransactionType.DELETE.getId().toString())
        val header = TransactionHeader(
            typeName = Constant.TransactionType.DELETE.getTypeName(), createDate = Constant.getCurrentTime(), totalProfit = 0
        )

        val transactionHeaderId = transactionHeaderDao.insert(header)

        val transactionDetail = TransactionDetail(
            transactionHeaderId = transactionHeaderId,
            createDate = header.createDate,
            productId = product.id!!,
            qty = 0,
            productName = product.productName,
            originalPrice = product.originalPrice,
            sellingPrice = product.sellingPrice,
            profit = product.profit,
        )
        transactionDetailDao.insert(transactionDetail)
    }

    suspend fun saveStockOut(productList: List<Product>, discount: Long, profit: Long) {

        val header = TransactionHeader(
            typeName = Constant.TransactionType.SALE.getTypeName(),
            createDate = Constant.getCurrentTime(),
            totalProfit = profit,
            discount = discount
        )
        val transactionHeaderId = transactionHeaderDao.insert(header)

        productList.forEach { product: Product ->
            Log.e("SAVE PRODUCT", product.toString())

            val mProduct = productDao.findProductById(product.id!!)
            mProduct.apply { currentQty -= product.currentQty }


            val transactionDetail = TransactionDetail(
                transactionHeaderId = transactionHeaderId,
                createDate = header.createDate,
                productId = product.id,
                qty = product.currentQty,
                productName = product.productName,
                originalPrice = product.originalPrice,
                sellingPrice = product.sellingPrice,
                profit = product.profit,
            )
            transactionDetailDao.insert(transactionDetail)
            productDao.update(mProduct)
        }
    }
}