package com.tzh.myshop.ui.shareComponent

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object Dimen {

    @Composable
    fun DefaultMarginWidth() = Spacer(modifier = Modifier.width(8.dp))

    @Composable
    fun DefaultMarginHeight() = Spacer(modifier = Modifier.height(8.dp))
    @Composable
    fun MarginSmallHeight() = Spacer(modifier = Modifier.height(4.dp))

    val paddingDefault = 8.dp
    val paddingSmall = 4.dp
    val defaultHeaderHeight = 40.dp
}