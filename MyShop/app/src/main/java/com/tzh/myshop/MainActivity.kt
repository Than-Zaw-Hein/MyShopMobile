package com.tzh.myshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.tzh.myshop.common.ulti.FloatingActionUIStates
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.common.navigation.nav_graph.NavGraph
import com.tzh.myshop.ui.shareComponent.MyTopAppBar
import com.tzh.myshop.ui.theme.MyShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


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
                    scaffoldState = scaffoldState, backgroundColor = Color.White,
                    topBar = {
                        MyTopAppBar(navController = navController, myRoute = myRoute, scaffoldState)
                    },
                    floatingActionButton = floatActionState.onFloatingActionButton ?: {},
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
