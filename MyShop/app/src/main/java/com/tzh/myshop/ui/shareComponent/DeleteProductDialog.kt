package com.tzh.myshop.ui.shareComponent

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tzh.myshop.common.ulti.PopUpState
import com.tzh.myshop.data.database.entity.Product

@Composable
fun DeleteProductDialog(deleteProduct: Product, onDelete: (Product) -> Unit, setIsShowDialog: (PopUpState) -> Unit) {
    Dialog(
        onDismissRequest = {
            setIsShowDialog(PopUpState.CLOSE)
        },
    ) {
        Card(backgroundColor = Color.White) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Confirm Delete!", style = MaterialTheme.typography.h6.copy(color = Color.Red))
                Dimen.DefaultMarginHeight()
                Text(
                    text = "Do you want to delete this product!\n",
                    style = MaterialTheme.typography.body2.copy(color = Color.Black)
                )
                MyShopButton.TextButton(title = "Confirm", modifier = Modifier.align(Alignment.End)) {
                    try {
                        onDelete.invoke(deleteProduct)
                    } catch (e: Exception) {
                        Log.e("Error", e.toString())
                    }
                }
            }
        }
    }
}