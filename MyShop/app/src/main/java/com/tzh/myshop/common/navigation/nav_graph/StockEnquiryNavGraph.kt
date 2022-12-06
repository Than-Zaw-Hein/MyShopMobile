package com.tzh.myshop.common.navigation.nav_graph

import androidx.compose.material.ScaffoldState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tzh.myshop.common.ulti.Constant.rememberParentEntry
import com.tzh.myshop.common.ulti.FloatingActionUIStates
import com.tzh.myshop.common.navigation.ROOT_STOCK_ENQUIRY_ROUTE
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.ui.screen.enquiry.EnquiryDetailScreen
import com.tzh.myshop.ui.screen.enquiry.EnquiryScreen
import com.tzh.myshop.ui.viewModel.EnquiryViewModel


fun NavGraphBuilder.stockEnquiryNavGraph(
    navController: NavController,
    scaffoldState: ScaffoldState,
    onComposing: ((state: FloatingActionUIStates) -> Unit)? = null,
    currentRoute: (Route) -> Unit
) {
    navigation(
        startDestination = Route.StockEnquiry.route, route = ROOT_STOCK_ENQUIRY_ROUTE
    ) {
        composable(route = Route.StockEnquiry.route) {
            currentRoute(Route.StockEnquiry)
            val parentEntry = it.rememberParentEntry(navController = navController)
            val viewModel: EnquiryViewModel = hiltViewModel(parentEntry)
            EnquiryScreen(navController, scaffoldState, viewModel)
        }

        composable(route = Route.StockEnquiryDetail.route) {
            currentRoute(Route.StockEnquiryDetail)
            val parentEntry = it.rememberParentEntry(navController = navController)
            val viewModel: EnquiryViewModel = hiltViewModel(parentEntry)
            EnquiryDetailScreen(navController, viewModel, onComposing = onComposing,scaffoldState)
        }
    }
}