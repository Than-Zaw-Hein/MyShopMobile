package com.tzh.myshop.common.navigation.nav_graph

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.tzh.myshop.common.ulti.FloatingActionUIStates
import com.tzh.myshop.common.navigation.ROOT_STOCK_ENQUIRY_ROUTE
import com.tzh.myshop.common.navigation.Route

fun NavGraphBuilder.stockEnquiryNavGraph(
    navController: NavController,
    scaffoldState: ScaffoldState,
    onComposing: ((state: FloatingActionUIStates) -> Unit)? = null,
    currentRoute: (Route) -> Unit
) {
    navigation(
        startDestination = Route.StockEnquiryDetail.route, route = ROOT_STOCK_ENQUIRY_ROUTE
    ) {
//        composable(route = Route.StockEnquiry.route) {
//            currentRoute(Route.StockEnquiry)
//            val parentEntry = it.rememberParentEntry(navController = navController)
//            val viewModel: EnquiryViewModel = hiltViewModel(parentEntry)
//            EnquiryScreen(navController, scaffoldState, viewModel)
//        }

//        composable(route = Route.StockEnquiryDetail.route) {
//            currentRoute(Route.StockEnquiryDetail)
//            val parentEntry = it.rememberParentEntry(navController = navController)
//            val viewModel: EnquiryViewModel = hiltViewModel(parentEntry)
//            EnquiryDetailScreen(navController, viewModel, onComposing = onComposing, scaffoldState)
//        }
    }
}