package com.tzh.myshop.common.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.tzh.myshop.R
import com.tzh.myshop.data.database.entity.Product


const val ROOT_ROUTE = "ROOT_ROUTE"
const val ROOT_HOME_ROUTE = "ROOT_HOME_ROUTE"
const val ROOT_STOCK_IN_ROUTE = "ROOT_STOCK_IN_ROUTE"
const val ROOT_STOCK_OUT_ROUTE = "ROOT_STOCK_OUT_ROUTE"
const val ROOT_STOCK_TAKE_ROUTE = "ROOT_STOCK_TAKE_ROUTE"
const val ROOT_STOCK_TRANSACTION_ROUTE = "ROOT_STOCK_TRANSACTION_ROUTE"
const val ROOT_STOCK_ENQUIRY_ROUTE = "ROOT_STOCK_ENQUIRY_ROUTE"

sealed class Route(val route: String, val title: String) {

    //Home
    object HOME : Route("Home", "My Shop")

    //StockIN
    object StockIN : Route("StockIn", "Stock In")

    //StockOut
    object StockOut : Route("StockOut", "Stock Out")

    //StockTake
    object StockTake : Route("StockTake", "Stock Take")

    //StockTransaction
    object StockTransaction : Route("StockTransaction", "Stock Transaction")
    object StockTransactionDetail :
        Route("StockTransactionDetail?header={header}&fromDate={fromDate}&toDate={toDate}", "Stock Transaction Detail") {
        fun navigateWithTransactionHeader(header: String, fromDate: String, toDate: String): String {
            return "StockTransactionDetail?header=$header&fromDate=$fromDate&toDate=$toDate"
        }
    }

    //Stock Enquiry
    object StockEnquiry : Route("StockEnquiry", "Stock Enquiry")
    object StockEnquiryDetail : Route("StockEnquiryDetail?product={product}", "Product Detail / Edit") {
        fun navigateWithProduct(product: String): String {
            return "StockEnquiryDetail?product=$product"
        }
    }
}

sealed class Screens(val route: String, @DrawableRes val icon: Int, var title: String) {

    object Home : Screens(ROOT_HOME_ROUTE, R.drawable.ic_stock_enquiry, "Home")

    object StockIN : Screens(ROOT_STOCK_IN_ROUTE, R.drawable.ic_stock_in, "Stock In")

    object StockTransaction : Screens(ROOT_STOCK_TRANSACTION_ROUTE, R.drawable.ic_transaction, "Record")

    object StockOut : Screens(ROOT_STOCK_OUT_ROUTE, R.drawable.ic_stock_out, "Stock Out")
}