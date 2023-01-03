package com.tzh.myshop.common.navigation.nav_graph

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tzh.myshop.common.navigation.ROOT_HOME_ROUTE
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.ui.screen.home.HomeScreen
import com.tzh.myshop.ui.screen.enquiry.EnquiryDetailScreen

fun NavGraphBuilder.homeNavGraph(navController: NavController, scaffoldState: ScaffoldState, currentRoute: (Route) -> Unit) {
    navigation(
        startDestination = Route.HOME.route, route = ROOT_HOME_ROUTE
    ) {
        composable(route = Route.HOME.route) {
            currentRoute(Route.HOME)
            HomeScreen(navController)
        }
        composable(
            route = Route.StockEnquiryDetail.route,
            arguments = listOf(
                navArgument(name = "product") {
                    type = NavType.StringType
                },
            ),
        ) {
            currentRoute(Route.StockEnquiryDetail)
            val product = Gson().fromJson<Product>(
                it.arguments?.getString("product")!!, object : TypeToken<Product>() {}.type
            )
            EnquiryDetailScreen(navController = navController, scaffoldState = scaffoldState, product = product)
        }
    }
}