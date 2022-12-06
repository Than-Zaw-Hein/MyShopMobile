package com.tzh.myshop.ui.shareComponent

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tzh.myshop.common.navigation.Route


@Composable
fun MyTopAppBar(navController: NavController, myRoute: Route, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = {
            Text(text = myRoute.title)
        },
        navigationIcon = if (myRoute == Route.HOME) null else {
            {
                IconButton(onClick = {
                    Log.e("CLICK 1", "TRUE")
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = null
                    )
                }
            }
        },
    )
}
