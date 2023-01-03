package com.tzh.myshop.ui.screen.transaction

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tzh.myshop.data.database.entity.TransactionDetail
import com.tzh.myshop.data.database.entity.TransactionHeader
import com.tzh.myshop.ui.shareComponent.BodyItem
import com.tzh.myshop.ui.shareComponent.Dimen
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault
import com.tzh.myshop.ui.shareComponent.TitleItem
import com.tzh.myshop.ui.theme.backgroundColor
import com.tzh.myshop.ui.viewModel.TransactionDetailViewModel
import com.tzh.myshop.ui.viewModel.TransactionViewModel

@Composable
fun TransactionDetailScreen(viewModel: TransactionDetailViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.transactionHeader != null) {
        if (uiState.isSale) {
            SaleDetailBody(
                uiState.transactionHeader!!, uiState.transactionDetail, uiState.selectedFromDate, uiState.selectedToDate
            )
        } else {
            if (uiState.transactionDetail.isNotEmpty()) {
                DetailBody(transactionDetail = uiState.transactionDetail[0], uiState.transactionHeader!!.typeName)
            }
        }
        Log.e("data", uiState.toString())
    }

}

@Composable
fun DetailBody(transactionDetail: TransactionDetail, type: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingDefault)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleItem(title = "Product Name", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            BodyItem(text = ": ${transactionDetail.productName}", modifier = Modifier.weight(2f))
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleItem(title = "Current Qty", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            BodyItem(text = ": ${transactionDetail.qty}", modifier = Modifier.weight(2f))
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleItem(title = "Original Price", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            BodyItem(text = ": ${transactionDetail.originalPrice}", modifier = Modifier.weight(2f))
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleItem(title = "Selling Name", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            BodyItem(text = ": ${transactionDetail.sellingPrice}", modifier = Modifier.weight(2f))
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleItem(title = "Type", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            BodyItem(text = ": $type", modifier = Modifier.weight(2f))
        }
    }

}


@Composable
fun SaleDetailBody(
    transactionHeader: TransactionHeader, transactionDetailList: List<TransactionDetail>, fromDate: String, toDate: String
) {
    val horizontalScrollState = rememberScrollState()
    val totalProfit = transactionDetailList.sumOf { it.profit * it.qty }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingDefault)

    ) {
        Column(
            Modifier
                .weight(1f)
                .horizontalScroll(state = horizontalScrollState)
        ) {
            ItemHeader()
            LazyColumn() {
                items(transactionDetailList) { detail: TransactionDetail ->
                    ListItem(detail = detail)
                    Dimen.DefaultMarginHeight()
                }
            }
        }

        Dimen.DefaultMarginHeight()
        Row(
            modifier = Modifier.align(Alignment.Start)
        ) {
            TitleItem(title = "Total Discount", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            BodyItem(text = ": ${transactionHeader.discount}", modifier = Modifier.weight(2f))
        }
        Dimen.DefaultMarginHeight()
        Row(
            modifier = Modifier.align(Alignment.Start)
        ) {
            TitleItem(title = "Total Profit", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            BodyItem(text = ": $totalProfit", modifier = Modifier.weight(2f))
        }
    }
}


@Composable
fun ItemHeader() {
    Surface(color = backgroundColor.copy(alpha = 0.6f), modifier = Modifier.padding(bottom = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TitleItem(title = "Product Name", modifier = Modifier.width(130.dp))
            TitleItem(title = "Qty", modifier = Modifier.width(60.dp))
            TitleItem(title = "Original Price", modifier = Modifier.width(110.dp))
            TitleItem(title = "Selling Price", modifier = Modifier.width(100.dp))
            TitleItem(title = "Profit", modifier = Modifier.width(100.dp))
        }
    }
}

@Composable
fun ListItem(detail: TransactionDetail) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
            .background(Color.Gray.copy(0.2f)),
    ) {
        BodyItem(text = detail.productName, modifier = Modifier.width(130.dp))
        BodyItem(text = detail.qty.toString(), modifier = Modifier.width(60.dp))
        BodyItem(text = detail.originalPrice.toString(), modifier = Modifier.width(110.dp))
        BodyItem(text = detail.sellingPrice.toString(), modifier = Modifier.width(100.dp))
        BodyItem(text = detail.profit.toString(), modifier = Modifier.width(100.dp))
    }
}

