package com.tzh.myshop.common.navigation.nav_graph

import androidx.compose.material.ScaffoldState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tzh.myshop.common.ulti.FloatingActionUIStates
import com.tzh.myshop.common.ulti.Constant.rememberParentEntry
import com.tzh.myshop.common.navigation.ROOT_STOCK_IN_ROUTE
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.ui.screen.stockIn.StockInScreen
import com.tzh.myshop.ui.viewModel.StockInViewModel


fun NavGraphBuilder.stockInNavGraph(
    navController: NavController,
    onComposing: ((state: FloatingActionUIStates) -> Unit)? = null,
    scaffoldState: ScaffoldState,
    currentRoute: (Route) -> Unit,
) {
    navigation(
        startDestination = Route.StockIN.route, route = ROOT_STOCK_IN_ROUTE
    ) {
        composable(route = Route.StockIN.route) {
            currentRoute(Route.StockIN)
            val parentEntry = it.rememberParentEntry(navController = navController)
            val viewModel: StockInViewModel = hiltViewModel(parentEntry)
            StockInScreen(navController, viewModel, onComposing, scaffoldState = scaffoldState)
        }
    }
}