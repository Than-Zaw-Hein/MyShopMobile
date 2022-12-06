package com.tzh.myshop.ui.viewModel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.tzh.myshop.R
import com.tzh.myshop.common.navigation.*
import com.tzh.myshop.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    val menuList = listOf(
        MenuItem(R.drawable.ic_stock_in, ROOT_STOCK_IN_ROUTE, Route.StockIN.title),
        MenuItem(R.drawable.ic_stock_out, ROOT_STOCK_OUT_ROUTE, Route.StockOut.title),
//        MenuItem(R.drawable.ic_stock_take, ROOT_STOCK_TAKE_ROUTE, Route.StockTake.title),
        MenuItem(R.drawable.ic_stock_enquiry, ROOT_STOCK_ENQUIRY_ROUTE, Route.StockEnquiry.title),
        MenuItem(R.drawable.ic_transaction, ROOT_STOCK_TRANSACTION_ROUTE, Route.StockTransaction.title)
    )
}

data class MenuItem(
    @DrawableRes val resourceId: Int, val navigateRoute: String, val title: String,
)
