package com.tzh.myshop.common.navigation.nav_graph

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tzh.myshop.common.ulti.FloatingActionUIStates
import com.tzh.myshop.common.navigation.ROOT_HOME_ROUTE
import com.tzh.myshop.common.navigation.ROOT_ROUTE
import com.tzh.myshop.common.navigation.ROOT_STOCK_ENQUIRY_ROUTE
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.ui.screen.enquiry.EnquiryDetailScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    floatingActionStates: ((FloatingActionUIStates) -> Unit)? = null,
    currentRoute: (Route) -> Unit
) {
    NavHost(
        navController = navController, startDestination = ROOT_HOME_ROUTE, route = ROOT_ROUTE
    ) {
        homeNavGraph(
            navController, scaffoldState = scaffoldState, currentRoute = currentRoute
        )

        stockInNavGraph(
            navController, onComposing = floatingActionStates, scaffoldState = scaffoldState, currentRoute = currentRoute,
        )
        stockOutNavGraph(
            navController, currentRoute = currentRoute,
        )
        stockTransactionNavGraph(
            navController, currentRoute = currentRoute,
        )
    }
}