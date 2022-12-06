package com.tzh.myshop.common.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tzh.myshop.common.navigation.ROOT_STOCK_OUT_ROUTE
import com.tzh.myshop.common.navigation.ROOT_STOCK_TAKE_ROUTE
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.ui.screen.HomeScreen


fun NavGraphBuilder.stockTakeNavGraph(navController: NavController, currentRoute: (Route) -> Unit) {
    navigation(
        startDestination = Route.StockTake.route, route = ROOT_STOCK_TAKE_ROUTE
    ) {
        composable(route = Route.StockTake.route) {
            currentRoute(Route.StockTake)
            HomeScreen(navController)
        }
    }
}