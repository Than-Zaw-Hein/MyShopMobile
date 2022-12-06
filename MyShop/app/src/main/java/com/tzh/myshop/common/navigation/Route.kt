package com.tzh.myshop.common.navigation


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
    object StockTransactionDetail : Route("StockTransactionDetail", "Stock Transaction Detail")

    //Stock Enquiry
    object StockEnquiry : Route("StockEnquiry", "Stock Enquiry")
    object StockEnquiryDetail : Route("StockEnquiryDetail", "Product Detail / Edit")

}