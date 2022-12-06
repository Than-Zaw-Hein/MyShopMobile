package com.tzh.myshop.common.navigation.nav_graph

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tzh.myshop.common.ulti.FloatingActionUIStates
import com.tzh.myshop.common.navigation.ROOT_HOME_ROUTE
import com.tzh.myshop.common.navigation.ROOT_ROUTE
import com.tzh.myshop.common.navigation.Route

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
            navController
        ) {
            currentRoute(it)
        }
        stockInNavGraph(
            navController, onComposing = floatingActionStates, scaffoldState = scaffoldState
        ) {
            currentRoute(it)
        }
        stockOutNavGraph(
            navController
        ) {
            currentRoute(it)
        }
        stockTakeNavGraph(
            navController
        ) {
            currentRoute(it)
        }
        stockTransactionNavGraph(
            navController
        ) {
            currentRoute(it)
        }
        stockEnquiryNavGraph(
            navController, scaffoldState = scaffoldState,
            onComposing = floatingActionStates
        ) {
            currentRoute(it)
        }

    }
}