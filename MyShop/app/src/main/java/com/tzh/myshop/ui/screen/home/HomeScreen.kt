@file:OptIn(ExperimentalFoundationApi::class)

package com.tzh.myshop.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tzh.myshop.common.ulti.PopUpState
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.ui.screen.product_detail.ProductDetailActivity
import com.tzh.myshop.ui.shareComponent.BodyItem
import com.tzh.myshop.ui.shareComponent.DeleteProductDialog
import com.tzh.myshop.ui.shareComponent.Dimen
import com.tzh.myshop.ui.shareComponent.TitleItem
import com.tzh.myshop.ui.theme.backgroundColor
import com.tzh.myshop.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSaveSuccess != null) {
        if (uiState.isDelete) {
            if (uiState.isSaveSuccess!!) {
                Toast.makeText(context, "Successfully delete", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Fail to delete this product", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.getData(uiState.productName)
        viewModel.doneAction()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.paddingDefault)
    ) {

        SearchBar(){
            viewModel.setProductNameTextChange(it)
        }

//        Card(
//            shape = CircleShape,
//            elevation = 16.dp,
//            modifier = Modifier.padding(Dimen.paddingDefault)
//        ) {
//            OutlinedTextField(
//                shape = CircleShape,
//                modifier = Modifier.fillMaxWidth(),
//                value = uiState.productName,
//                onValueChange = {
//                    viewModel.setProductNameTextChange(it)
//                    viewModel.getData(it)
//                },
//                singleLine = true,
//                textStyle = MaterialTheme.typography.body2,
//                placeholder = {
//                    Text(text = "Search with Product Name")
//                },
//                leadingIcon = {
//                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
////                    IconButton(onClick = {
////                        viewModel.getData(uiState.productName)
////                    }) {
////
////                    }
//                },
//            )
//        }
        Dimen.DefaultMarginHeight()
        ListBody(
            uiState.productList,
            onClick = {
                context.apply {
                    startActivity(ProductDetailActivity.getNewIntent(this, it))
                }
            },
            onDelete = {
                viewModel.deleteProduct(it)
            },
        )
    }
}


@Composable
fun ColumnScope.ListBody(
    mList: List<Product>,
    onClick: (Product) -> Unit,
    onDelete: (Product) -> Unit
) {
    val scrollState = rememberScrollState()
    var isShowDialog by remember { mutableStateOf(PopUpState.CLOSE) }
    var deleteProduct: Product? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .weight(1f)
            .horizontalScroll(state = scrollState)
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
    Surface(
        color = backgroundColor.copy(alpha = 0.6f),
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search...", color = Color.White) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "", tint = Color.White)
        },
        trailingIcon = {
            if (searchQuery.text.isNotEmpty()) {
                IconButton(onClick = {
                    searchQuery = TextFieldValue("")
                    keyboardController?.hide()
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search",
                        tint = Color.Red
                    )
                }
            }
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Handle the search action
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Gray,
            textColor = Color.White,

            )
    )
}
