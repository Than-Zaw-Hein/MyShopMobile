package com.tzh.myshop.ui.screen.product_detail

import android.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tzh.myshop.MainActivity
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.ui.shareComponent.ChoosePhotoLayout
import com.tzh.myshop.ui.shareComponent.Dimen
import com.tzh.myshop.ui.shareComponent.MyTextFieldWithTitle
import com.tzh.myshop.ui.theme.MyShopTheme
import com.tzh.myshop.ui.theme.SecondaryColor
import com.tzh.myshop.ui.theme.PrimaryCharcoal
import com.tzh.myshop.ui.viewModel.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailActivity : ComponentActivity() {

    private val product: Product by lazy {
        intent?.getSerializableExtra(PRODUCT) as Product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShopTheme {
                EnquiryDetailScreen(product = product) { isSave ->
                    if (isSave) {
                        val newIntent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(newIntent)
                    } else {
                        super.onBackPressed()
                    }
                }
            }
        }
    }

    companion object {
        private val PRODUCT = "PRODUCT"
        fun getNewIntent(context: Context, product: Product): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT, product)
            }
        }
    }
}


@Composable
fun EnquiryDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(), product: Product, navigateBack: (isSave: Boolean) -> Unit
) {
    LaunchedEffect(key1 = product, block = {
        viewModel.setProduct(product)
    })
    val uiState by viewModel.uiState.collectAsState()
    var imageList = viewModel.imageList
    val context = LocalContext.current
    if (!uiState.error.isNullOrEmpty()) {
        Toast.makeText(context, uiState.error.toString(), Toast.LENGTH_LONG).show()
        viewModel.doneShowErrorMessage()
    }

    if (uiState.isSaveSuccess != null) {
        if (uiState.isSaveSuccess!!) {
            Toast.makeText(context, "Successfully save", Toast.LENGTH_LONG).show()
            viewModel.doneAction()
            navigateBack.invoke(true)
        } else {
            Toast.makeText(context, "Fail to save \nError Message : ${uiState.error}", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            val appBarHorizontalPadding = 4.dp
            val titleIconModifier = Modifier
                .fillMaxHeight()
                .width(72.dp - appBarHorizontalPadding)

            TopAppBar(
                backgroundColor = Color.Transparent,
                contentColor = contentColorFor(backgroundColor = Color.Black),
                elevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(Modifier.height(32.dp)) {

                    //Navigation Icon
                    Row(titleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ) {
                            IconButton(onClick = {
                                Log.e("CLICK 1", "TRUE")
                                navigateBack.invoke(false)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack, contentDescription = null
                                )
                            }
                        }
                    }

                    //Title
                    Row(
                        Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
                    ) {

                        ProvideTextStyle(value = MaterialTheme.typography.h6) {
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.high,
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    text = "Product Detail"
                                )
                            }
                        }
                    }

                    //actions
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Row(
                            Modifier.fillMaxHeight(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            content = {},
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = PrimaryCharcoal,
                onClick = {
                    viewModel.updateProduct()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_save), tint = Color.White, contentDescription = null
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            MyTextFieldWithTitle(
                title = "ProductName",
                value = viewModel.productName,
                onValueChange = { viewModel.productName = it })
            Dimen.DefaultMarginHeight()

            MyTextFieldWithTitle(
                title = "Qty",
                value = "${viewModel.qty}",
                onValueChange = viewModel.qtyTextChange,
                isTypeNumber = true,
                hasSuffix = false
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
                    .padding(top = Dimen.paddingDefault)
                    .border(1.dp, SecondaryColor.copy(alpha = 0.5f))
            ) {
                Text(
                    text = "${viewModel.profit} MMK",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimen.paddingDefault),
                )
            }
            ChoosePhotoLayout(
                addImage = { uri: Uri, i: Int ->
                    viewModel.addImage(uri, i)
                }, imageList = imageList
            )
        }
    }

}

