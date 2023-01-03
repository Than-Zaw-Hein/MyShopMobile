package com.tzh.myshop.common.navigation.nav_graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tzh.myshop.common.ulti.Constant.rememberParentEntry
import com.tzh.myshop.common.navigation.ROOT_STOCK_OUT_ROUTE
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.ui.screen.stockOut.StockOutScreen
import com.tzh.myshop.ui.viewModel.StockOutViewModel

fun NavGraphBuilder.stockOutNavGraph(navController: NavController, currentRoute: (Route) -> Unit) {
    navigation(
        startDestination = Route.StockOut.route, route = ROOT_STOCK_OUT_ROUTE
    ) {
        composable(route = Route.StockOut.route) {
            currentRoute(Route.StockOut)
//            val parentEntry = it.rememberParentEntry(navController = navController)
//            val viewModel: StockOutViewModel = hiltViewModel(parentEntry)
            StockOutScreen(navController)
        }
    }
}