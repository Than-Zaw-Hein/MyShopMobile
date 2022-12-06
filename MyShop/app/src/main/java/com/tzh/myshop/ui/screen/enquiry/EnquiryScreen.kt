@file:OptIn(ExperimentalFoundationApi::class)

package com.tzh.myshop.ui.screen.enquiry

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzh.myshop.common.ulti.PopUpState
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.ui.shareComponent.*
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault
import com.tzh.myshop.ui.theme.backgroundColor
import com.tzh.myshop.ui.viewModel.EnquiryViewModel

@Composable
fun EnquiryScreen(
    navController: NavController, scaffoldState: ScaffoldState, viewModel: EnquiryViewModel
) {
    val context = LocalContext.current
    val list = viewModel.filterList
    var productName by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSaveSuccess != null) {
        if (uiState.isDelete) {
            if (uiState.isSaveSuccess!!) {
                Toast.makeText(context, "Successfully delete", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Fail to delete this product", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.getData(productName)
        viewModel.doneAction()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingDefault)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingDefault),
            value = productName,
            onValueChange = {
                productName = it
                viewModel.getData(productName)
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.body2,
            placeholder = {
                Text(text = "Search with Product Name")
            },
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.getData(productName)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            },
        )
        Dimen.DefaultMarginHeight()
        ListBody(
            list,
            onClick = {
                viewModel.goToDetail(product = it)
                navController.navigate(Route.StockEnquiryDetail.route)
            },
            onDelete = {
                viewModel.deleteProduct(it)
            },
        )

    }
}

@Composable
fun ColumnScope.ListBody(mList: List<Product>, onClick: (Product) -> Unit, onDelete: (Product) -> Unit) {
    val scaffoldState = rememberScrollState()
    var isShowDialog by remember { mutableStateOf(PopUpState.CLOSE) }
    var deleteProduct: Product? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .weight(1f)
            .horizontalScroll(state = scaffoldState)
    ) {
        ItemHeader()
        LazyColumn() {
            items(mList) { item: Product ->
                ListItem(
                    product = item,
                    onClick = onClick,
                    isConfirmDelete = {
                        deleteProduct = it
                        isShowDialog = PopUpState.OPEN
                    },
                )
                Dimen.DefaultMarginHeight()
            }
        }
    }

    if (isShowDialog == PopUpState.OPEN) {
        DeleteProductDialog(deleteProduct!!, onDelete = {
            onDelete.invoke(it)
            isShowDialog = PopUpState.CLOSE
        }, setIsShowDialog = {
            deleteProduct = null
            isShowDialog = PopUpState.CLOSE
        })
    }
}

@Composable
fun ItemHeader() {
    Surface(color = backgroundColor.copy(alpha = 0.6f), modifier = Modifier.padding(bottom = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TitleItem(title = "Product Name", modifier = Modifier.width(130.dp))
            TitleItem(title = "Qty", modifier = Modifier.width(60.dp))
            TitleItem(title = "Current Qty", modifier = Modifier.width(64.dp))
            TitleItem(title = "Original Price", modifier = Modifier.width(110.dp))
            TitleItem(title = "Selling Price", modifier = Modifier.width(100.dp))
        }
    }
}

@Composable
fun ListItem(product: Product, onClick: (Product) -> Unit, isConfirmDelete: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
            .background(Color.Gray.copy(0.2f))
            .combinedClickable(
                onClick = { onClick(product) },
                onLongClick = { isConfirmDelete(product) },
            ),
    ) {
        BodyItem(text = product.productName, modifier = Modifier.width(130.dp))
        BodyItem(text = product.qty.toString(), modifier = Modifier.width(60.dp))
        BodyItem(text = product.currentQty.toString(), modifier = Modifier.width(64.dp))
        BodyItem(text = product.originalPrice.toString(), modifier = Modifier.width(110.dp))
        BodyItem(text = product.sellingPrice.toString(), modifier = Modifier.width(100.dp))
    }
}
