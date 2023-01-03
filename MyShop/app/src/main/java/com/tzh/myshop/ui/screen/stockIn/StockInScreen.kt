package com.tzh.myshop.ui.screen.stockIn

import android.R
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tzh.myshop.common.ulti.FloatingActionUIStates
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.ui.shareComponent.ChoosePhotoLayout
import com.tzh.myshop.ui.shareComponent.Dimen
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault
import com.tzh.myshop.ui.shareComponent.MyTextFieldWithTitle
import com.tzh.myshop.ui.shareComponent.MyTopAppBar
import com.tzh.myshop.ui.theme.accentAmber
import com.tzh.myshop.ui.theme.primaryCharcoal
import com.tzh.myshop.ui.viewModel.StockInViewModel

@Composable
fun StockInScreen(
    navController: NavController,
    onComposing: ((state: FloatingActionUIStates) -> Unit)? = null,
    scaffoldState: ScaffoldState,
    viewModel: StockInViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    val imageList = viewModel.imageList
    val context = LocalContext.current
    if (!uiState.error.isNullOrEmpty()) {
        Toast.makeText(context, uiState.error.toString(), Toast.LENGTH_LONG).show()
        viewModel.doneShowErrorMessage()
    }

    if (uiState.isSaveSuccess != null) {
        if (uiState.isSaveSuccess!!) {
            Toast.makeText(context, "Successfully save", Toast.LENGTH_LONG).show()
            navController.navigate(Route.HOME.route) {
                popUpTo(Route.HOME.route) {
                    inclusive = true
                }
            }
        } else {
            Toast.makeText(context, "Fail to save this product", Toast.LENGTH_LONG).show()
        }
        viewModel.doneAction()
    }

    onComposing?.invoke(
        FloatingActionUIStates(
            onFloatingActionButton = {
                FloatingActionButton(
                    backgroundColor = primaryCharcoal,
                    onClick = {
                        viewModel.createProduct()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu_save), tint = Color.White, contentDescription = null
                    )
                }
            },
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        MyTextFieldWithTitle(title = "ProductName", value = viewModel.productName, onValueChange = { viewModel.productName = it })
        Dimen.DefaultMarginHeight()
        MyTextFieldWithTitle(
            title = "Qty", value = viewModel.qty, onValueChange = viewModel.qtyTextChange, isTypeNumber = true, hasSuffix = false
        )
        Dimen.DefaultMarginHeight()

        Row(verticalAlignment = Alignment.CenterVertically) {
            MyTextFieldWithTitle(
                modifier = Modifier.weight(1f),
                title = "Original Price",
                value = "${viewModel.originalPrice}",
                onValueChange = viewModel.orgPriceTextChange,
                isTypeNumber = true,
                hasSuffix = true
            )
            Dimen.DefaultMarginWidth()
            MyTextFieldWithTitle(
                modifier = Modifier.weight(1f),
                title = "Selling Price",
                value = "${viewModel.sellingPrice}",
                onValueChange = viewModel.sellingPriceTextChange,
                isTypeNumber = true,
                hasSuffix = true
            )

        }
        Dimen.DefaultMarginHeight()
        Text(text = "Profit", style = MaterialTheme.typography.h6)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingDefault)
                .border(1.dp, accentAmber.copy(alpha = 0.5f))

        ) {
            Text(
                text = "${viewModel.profit} MMK",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingDefault),
            )
        }
        ChoosePhotoLayout(
            addImage = { uri: Uri, i: Int ->
                viewModel.addImage(uri, i)
            }, imageList = imageList
        )
    }
}
