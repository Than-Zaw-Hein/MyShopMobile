package com.tzh.myshop.ui.shareComponent

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault


@Composable
fun LoadingDialog(message: String? = null) {

    Dialog(onDismissRequest = { }) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(paddingDefault * 2),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(paddingDefault * 2),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                if (message != null) {
                    Dimen.DefaultMarginWidth()
                    TitleItem(title = message)
                }

            }
        }
    }
}