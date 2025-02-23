package com.tzh.myshop.common.navigation.nav_graph

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tzh.myshop.common.navigation.ROOT_HOME_ROUTE
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.ui.screen.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavController, scaffoldState: ScaffoldState, currentRoute: (Route) -> Unit) {
    navigation(
        startDestination = Route.HOME.route, route = ROOT_HOME_ROUTE
    ) {
        composable(route = Route.HOME.route) {
            currentRoute(Route.HOME)
            HomeScreen(navController)
        }
    }
}