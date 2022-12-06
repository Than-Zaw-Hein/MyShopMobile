package com.tzh.myshop.ui.shareComponent

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tzh.myshop.R
import com.tzh.myshop.ui.theme.MyShopTheme

@Composable
fun MyCustomButton(@DrawableRes id: Int, modifier: Modifier = Modifier, title: String, onclick: () -> Unit) {

    Card(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .clickable {
                onclick.invoke()
            }, elevation = 8.dp, border = BorderStroke(1.dp, Color.Blue.copy(alpha = 0.2f))
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .size(90.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Inside,
                painter = painterResource(id = id),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp), text = title, style = MaterialTheme.typography.h6
            )
        }
    }
}

