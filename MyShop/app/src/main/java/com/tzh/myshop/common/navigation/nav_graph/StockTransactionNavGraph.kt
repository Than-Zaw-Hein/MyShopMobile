package com.tzh.myshop.common.navigation.nav_graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
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
            TransactionScreen(navController, hiltViewModel())
        }

        composable(route = Route.StockTransactionDetail.route, arguments = listOf(navArgument("header") {
            type = NavType.StringType
        }, navArgument("fromDate") {
            type = NavType.StringType
        }, navArgument("toDate") {
            type = NavType.StringType
        })) {
            currentRoute(Route.StockTransactionDetail)
            TransactionDetailScreen(hiltViewModel())
        }
    }
}
