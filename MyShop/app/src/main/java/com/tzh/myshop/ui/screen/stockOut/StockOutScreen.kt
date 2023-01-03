package com.tzh.myshop.ui.screen.stockOut

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tzh.myshop.common.ulti.Extension.showToast
import com.tzh.myshop.common.ulti.Extension.toPrice
import com.tzh.myshop.common.ulti.PopUpState
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.ui.shareComponent.*
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault
import com.tzh.myshop.ui.theme.backgroundColor
import com.tzh.myshop.ui.viewModel.StockOutViewModel

@Composable
fun StockOutScreen(navController: NavController, viewModel: StockOutViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var isAddProductShowDialog by remember { mutableStateOf(PopUpState.CLOSE) }
    var isShowDialog by remember { mutableStateOf(PopUpState.CLOSE) }
    var isConfirmDialog by remember { mutableStateOf(PopUpState.CLOSE) }

    val productList = viewModel.productList

    val errorMessage = viewModel.errorMessage.value
    if (!errorMessage.isNullOrEmpty()) {
        context.showToast(errorMessage)
        viewModel.doneShowErrorMessage()
    }
    val isLoading = viewModel.isLoading.value
    if (isLoading) {
        LoadingDialog("Saving please wait.....")
    }
    if (viewModel.isSuccessSave.value) {
        navController.navigateUp()
        viewModel.doneAction()
    }

    var deleteProduct: Product? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingDefault)
            .padding(bottom = 56.dp)
    ) {
        ItemHeader()
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(productList, key = {
                it.id!!
            }, itemContent = { item: Product ->
                ListItem(
                    product = item,
                    onClick = {

                    },
                    isConfirmDelete = {
                        deleteProduct = it
                        isShowDialog = PopUpState.OPEN
                    },
                    updateItem = {
                        viewModel.updateProduct(it)
                    },
                )
                Dimen.DefaultMarginHeight()
            })
        }

        MyShopButton.SideButton(
            title = "Add Product", modifier = Modifier.fillMaxWidth()
        ) {
            isAddProductShowDialog = PopUpState.OPEN
        }

        MyShopButton.PerformButton(
            title = "Confirm Stock Out", modifier = Modifier.fillMaxWidth()
        ) {
            if (productList.isEmpty()) {
                context.showToast("Product must not be empty")
            } else {
                isConfirmDialog = PopUpState.OPEN
            }
        }
    }

    if (isAddProductShowDialog == PopUpState.OPEN) {
        AddProduct(
            onDismiss = {
                isAddProductShowDialog = it
            },
            viewModel = viewModel,
            onClick = {
                it.currentQty = 1
                viewModel.addProduct(it)
                isAddProductShowDialog = PopUpState.CLOSE
            },
        )
    }

    if (isShowDialog == PopUpState.OPEN) {
        DeleteProductDialog(
            deleteProduct!!,
            onDelete = {
                viewModel.removeProduct(it)
                isShowDialog = PopUpState.CLOSE
            },
            setIsShowDialog = {
                deleteProduct = null
                isShowDialog = PopUpState.CLOSE
            },
        )
    }

    if (isConfirmDialog == PopUpState.OPEN) {
        ConfirmDialog(
            onDismiss = {
                isConfirmDialog = it
            },
            viewModel = viewModel,
        )
    }
}

@Composable
fun ItemHeader() {
    Surface(color = backgroundColor.copy(alpha = 0.6f), modifier = Modifier.padding(bottom = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleItem(title = "Product Name", modifier = Modifier.weight(1f))
            TitleItem(
                title = "Qty",
                modifier = Modifier.weight(.6f),
                style = MaterialTheme.typography.h6.copy(fontSize = 15.sp, textAlign = TextAlign.Center)
            )
            TitleItem(title = "Selling Price", modifier = Modifier.weight(.9f))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItem(
    product: Product, onClick: (Product) -> Unit = {}, isConfirmDelete: (Product) -> Unit = {}, updateItem: (Product) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(0.2f))
            .combinedClickable(
                onClick = { onClick(product) },
                onLongClick = { isConfirmDelete(product) },
            ),
    ) {
        BodyItem(text = product.productName, modifier = Modifier.weight(1f))
        Row(
            Modifier.weight(.6f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                updateItem(
                    product // Getter
                        .copy( //Convenience Method
                            currentQty = if (product.currentQty > 1) {
                                product.currentQty - 1 // Increase current Quantity
                            } else product.currentQty
                        )
                )
            }, modifier = Modifier.weight(.3f)) {
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = null)
            }
            BodyItem(
                text = product.currentQty.toString(), modifier = Modifier.weight(.6f), textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                updateItem(
                    product // Getter
                        .copy( //Convenience Method
                            currentQty = if (product.currentQty < product.qty) {
                                product.currentQty + 1 // Increase current Quantity
                            } else product.currentQty
                        )
                )
            }, modifier = Modifier.weight(.3f)) {
                Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null)
            }
        }
        BodyItem(text = product.sellingPrice.toString(), modifier = Modifier.weight(.9f))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddProduct(
    onDismiss: (PopUpState) -> Unit, viewModel: StockOutViewModel, onClick: (Product) -> Unit
) {
    var productName by remember { mutableStateOf("") }
    val scaffoldState = rememberScrollState()
    viewModel.getData(productName)
    val list = viewModel.filterList
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            onDismiss(PopUpState.CLOSE)
        },
    ) {
        Card(
            backgroundColor = Color.White, modifier = Modifier
                .fillMaxSize()
                .padding(paddingDefault)
        ) {
            Column(
                modifier = Modifier.padding(paddingDefault), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        onDismiss(PopUpState.CLOSE)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = Color.Red,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = paddingDefault),
                    value = productName,
                    onValueChange = {
                        productName = it
//                            viewModel.getData(productName)
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body2,
                    placeholder = {
                        Text(text = "Search with Product Name")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.getData(productName)
                            },
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        }
                    },
                )
                Dimen.DefaultMarginHeight()
                ItemHeader()
                LazyColumn() {
                    items(list) { item: Product ->

                        Row(
                            modifier = Modifier
                                .height(35.dp)
                                .fillMaxWidth()
                                .background(Color.Gray.copy(0.2f))
                                .clickable { onClick(item) }, verticalAlignment = Alignment.CenterVertically
                        ) {
                            BodyItem(text = item.productName, modifier = Modifier.weight(1f))
                            BodyItem(
                                text = item.currentQty.toString(), modifier = Modifier.weight(.6f), textAlign = TextAlign.Center
                            )
                            BodyItem(text = item.sellingPrice.toString(), modifier = Modifier.weight(.9f))
                        }
                        Dimen.DefaultMarginHeight()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConfirmDialog(onDismiss: (PopUpState) -> Unit, viewModel: StockOutViewModel) {
    var discount by remember { mutableStateOf("0") }
    val discountTextChange: (String) -> Unit = {
        discount = it.toPrice()
    }
    val totalQty = viewModel.productList.map { it.currentQty }.sumOf { it }
    val totalAmount = (viewModel.productList.sumOf { it.currentQty * it.sellingPrice }) - discount.toLong()
    val profit = viewModel.productList.map { it.profit * it.currentQty }.sumOf { it }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            onDismiss(PopUpState.CLOSE)
        },
    ) {
        Card(
            backgroundColor = Color.White, modifier = Modifier
                .fillMaxSize()
                .padding(paddingDefault)
        ) {
            Column(
                modifier = Modifier.padding(paddingDefault), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = paddingDefault),
                    onClick = {
                        onDismiss(PopUpState.CLOSE)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = Color.Red,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
                ItemHeader()
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.productList, key = {
                        it.id!!
                    }, itemContent = { product: Product ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray.copy(0.2f))
                        ) {
                            BodyItem(text = product.productName, modifier = Modifier.weight(1f))
                            BodyItem(
                                text = product.currentQty.toString(),
                                modifier = Modifier.weight(.6f),
                                textAlign = TextAlign.Center
                            )
                            BodyItem(text = product.sellingPrice.toString(), modifier = Modifier.weight(.9f))
                        }
                        Dimen.DefaultMarginHeight()
                    })
                }
                Dimen.DefaultMarginHeight()
                MyTextFieldWithTitle(
                    title = "Discount",
                    value = discount,
                    onValueChange = discountTextChange,
                    isTypeNumber = true,
                    hasSuffix = true,
                )
                Dimen.DefaultMarginHeight()
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TitleItem(title = "Total Qty", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    BodyItem(text = ": $totalQty", modifier = Modifier.weight(2f))
                }
                Dimen.DefaultMarginHeight()
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TitleItem(title = "Discount", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    BodyItem(text = ": ${discount.toLong()}", modifier = Modifier.weight(2f))
                }
                Dimen.DefaultMarginHeight()
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TitleItem(title = "Total Amount", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    BodyItem(text = ": $totalAmount", modifier = Modifier.weight(2f))
                }
                MyShopButton.PerformButton(
                    title = "Confirm", modifier = Modifier.fillMaxWidth()
                ) {
                    onDismiss(PopUpState.CLOSE)
                    viewModel.save(discount.toLong(), profit)
                }
            }
        }
    }
}
