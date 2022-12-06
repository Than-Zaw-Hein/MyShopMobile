package com.tzh.myshop.common.navigation.nav_graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tzh.myshop.common.ulti.Constant.rememberParentEntry
import com.tzh.myshop.common.navigation.ROOT_STOCK_TRANSACTION_ROUTE
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.ui.screen.transaction.TransactionDetailScreen
import com.tzh.myshop.ui.screen.transaction.TransactionScreen
import com.tzh.myshop.ui.viewModel.TransactionViewModel


fun NavGraphBuilder.stockTransactionNavGraph(navController: NavController, currentRoute: (Route) -> Unit) {
    navigation(
        startDestination = Route.StockTransaction.route, route = ROOT_STOCK_TRANSACTION_ROUTE
    ) {
        composable(route = Route.StockTransaction.route) {
            currentRoute(Route.StockTransaction)
            val parentEntry = it.rememberParentEntry(navController = navController)
            val viewModel: TransactionViewModel = hiltViewModel(parentEntry)
            TransactionScreen(navController, viewModel)
        }

        composable(route = Route.StockTransactionDetail.route) {
            currentRoute(Route.StockTransactionDetail)
            val parentEntry = it.rememberParentEntry(navController = navController)
            val viewModel: TransactionViewModel = hiltViewModel(parentEntry)
            TransactionDetailScreen(navController, viewModel)
        }
    }
}
