package com.tzh.myshop.ui.shareComponent

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TitleItem(title: String, modifier: Modifier = Modifier,style :TextStyle = MaterialTheme.typography.h6.copy(fontSize = 15.sp)) {
    Text(
        text = title, modifier = modifier.padding(4.dp), style = style
    )
}
