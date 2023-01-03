package com.tzh.myshop

import android.R
import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tzh.myshop.common.navigation.*
import com.tzh.myshop.common.navigation.nav_graph.NavGraph
import com.tzh.myshop.common.ulti.FloatingActionUIStates
import com.tzh.myshop.ui.shareComponent.MyTopAppBar
import com.tzh.myshop.ui.theme.MyShopTheme
import com.tzh.myshop.ui.theme.primaryCharcoal
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val screens = listOf(
        Screens.Home,
        Screens.StockIN,
        Screens.StockOut,
        Screens.StockTransaction,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShopTheme {

                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                var myRoute: Route by remember {
                    mutableStateOf(Route.HOME)
                }
                var floatActionState by remember {
                    mutableStateOf(FloatingActionUIStates())
                }
                if (myRoute == Route.StockIN || myRoute == Route.StockEnquiryDetail) {
                } else {
                    floatActionState = FloatingActionUIStates()
                }
                Scaffold(
                    scaffoldState = scaffoldState,
                    backgroundColor = Color.White,
                    topBar = {
                        AnimatedVisibility(
                            visible = myRoute == Route.StockEnquiryDetail || myRoute == Route.StockTransactionDetail,
                            enter = fadeIn(tween(1000)) + expandVertically(
                                animationSpec = tween(
                                    800,
                                ),
                            ),
                            exit = fadeOut(tween(1000)) + shrinkVertically(
                                animationSpec = tween(
                                    800,
                                )
                            )
                        ) {
                            MyTopAppBar(navController = navController, myRoute = myRoute, scaffoldState)
                        }
                    },
                    floatingActionButton = floatActionState.onFloatingActionButton ?: {},
//                    isFloatingActionButtonDocked = true,
                    bottomBar = {
                        AnimatedVisibility(
                            visible = myRoute != Route.StockEnquiryDetail, enter = fadeIn(tween(1000)) + expandVertically(
                                animationSpec = tween(
                                    800,
                                ),
                            ), exit = fadeOut(tween(1000)) + shrinkVertically(
                                animationSpec = tween(
                                    800,
                                )
                            )
                        ) {
                            BottomNavigation(backgroundColor = Color.White, contentColor = Color.Black) {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                screens.forEach { screen ->
                                    BottomNavigationItem(icon = {
                                        Image(
                                            modifier = Modifier
                                                .size(30.dp)
                                                .padding(4.dp),
                                            contentScale = ContentScale.Inside,
                                            painter = painterResource(id = screen.icon),
                                            contentDescription = null
                                        )
                                    },
                                        label = { Text(screen.title) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }
                                        })
                                }
                            }
                        }
                    },
                ) {
                    NavGraph(navController, scaffoldState = scaffoldState, floatingActionStates = {
                        floatActionState = it
                    }) {
                        myRoute = it
                    }
                }
            }
        }
    }
}
