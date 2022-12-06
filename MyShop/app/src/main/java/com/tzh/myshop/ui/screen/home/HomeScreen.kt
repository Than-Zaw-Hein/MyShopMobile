package com.tzh.myshop.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tzh.myshop.ui.shareComponent.MyCustomButton
import com.tzh.myshop.ui.viewModel.HomeViewModel
import com.tzh.myshop.ui.viewModel.MenuItem

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(viewModel.menuList) { menu: MenuItem ->
            MyCustomButton(id = menu.resourceId, title = menu.title) {
                navController.navigate(menu.navigateRoute)
            }
        }
    }
}