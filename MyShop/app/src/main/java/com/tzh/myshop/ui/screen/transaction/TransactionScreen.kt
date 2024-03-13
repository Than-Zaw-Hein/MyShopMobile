package com.tzh.myshop.ui.screen.transaction


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzh.myshop.common.ulti.Constant
import com.tzh.myshop.common.navigation.Route
import com.tzh.myshop.common.ulti.Extension.toJson
import com.tzh.myshop.data.database.entity.TransactionHeader
import com.tzh.myshop.ui.shareComponent.*
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault
import com.tzh.myshop.ui.theme.backgroundColor
import com.tzh.myshop.ui.viewModel.TransactionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionScreen(navController: NavController, viewModel: TransactionViewModel) {

    val transactionType by viewModel.selectType
    val transactionList = viewModel.transactionHeaderList
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingDefault)
    ) {
        MyDropDownWithTitle(
            title = "Filter by Transaction Type",
            dropDownList = viewModel.typeList.map { it.getTypeName() },
            defaultValue = viewModel.selectType.value,
            isNecessary = false,
            selectValue = viewModel.setSelectTypeList,
        )
        Dimen.DefaultMarginHeight()
        Row(modifier = Modifier.fillMaxWidth()) {

            MyDatePickerWithTitle(
                title = "From Date",
                defaultValue = viewModel.selectFromDate.value,
                selectValue = viewModel.setSelectFromDate,
                modifier = Modifier.weight(1f)
            )
            Dimen.DefaultMarginWidth()

            MyDatePickerWithTitle(
                title = "To Date",
                defaultValue = viewModel.selectToDate.value,
                selectValue = viewModel.setSelectToDate,
                modifier = Modifier.weight(1f)
            )

        }
        Dimen.DefaultMarginHeight()
        MyShopButton.SideButton(
            title = "Search", modifier = Modifier
                .align(Alignment.End)
                .fillMaxWidth(), shape = RoundedCornerShape(20.dp)
        ) {
            viewModel.getTransactionHeader()
        }
        Dimen.DefaultMarginHeight()
        Column(modifier = Modifier.weight(1f)) {
            ItemHeader(transactionType)
            LazyColumn() {
                items(transactionList) { item: TransactionHeader ->
                    ListItem(
                        transactionHeader = item, transactionType = transactionType,
                        onClick = {
                            navController.navigate(
                                Route.StockTransactionDetail.navigateWithTransactionHeader(
                                    it.toJson(), viewModel.selectFromDate.value, viewModel.selectToDate.value
                                )
                            )
                        },
                    )
                    Dimen.DefaultMarginHeight()
                }
            }
        }
    }
}

@Composable
fun ItemHeader(transactionType: String) {
    Surface(color = backgroundColor.copy(alpha = 0.6f), modifier = Modifier.padding(bottom = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TitleItem(title = "Transaction Type", modifier = Modifier.weight(1f))
            TitleItem(title = "Create Date", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ListItem(transactionHeader: TransactionHeader, transactionType: String, onClick: (TransactionHeader) -> Unit) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
            .background(Color.Gray.copy(0.2f))
            .clickable {
                onClick(transactionHeader)
            },
    ) {
        BodyItem(text = transactionHeader.typeName, modifier = Modifier.weight(1f))
        BodyItem(text = transactionHeader.createDate, modifier = Modifier.weight(1f))
    }
}
