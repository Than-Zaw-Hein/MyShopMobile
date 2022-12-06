package com.tzh.myshop.ui.shareComponent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tzh.myshop.ui.theme.accentAmber

object MyShopButton {

    @Composable
    fun MainButton(title: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
        Button(
            onClick = onClick, modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            ButtonText(text = title)
        }
    }

    @Composable
    fun SideButton(title: String, modifier: Modifier = Modifier, shape: Shape = MaterialTheme.shapes.small, onClick: () -> Unit) {
        Button(
            onClick = onClick, modifier = modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            ), shape = shape
        ) {
            ButtonText(text = title)
        }
    }

    @Composable
    fun PerformButton(title: String, modifier: Modifier = Modifier, isEnabled: Boolean = true, onClick: () -> Unit) {
        Button(
            onClick = onClick, modifier = modifier, colors = ButtonDefaults.buttonColors(
                backgroundColor = accentAmber, contentColor = Color.White
            ), enabled = isEnabled
        ) {
            ButtonText(text = title)
        }
    }

    @Composable
    fun ButtonText(text: String) {
        Text(text = text, fontSize = 18.sp)
    }

    @Composable
    fun TextButton(title: String, modifier: Modifier = Modifier, onClick: () -> Unit) {

        TextButton(
            modifier = modifier,
            onClick = onClick,

            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.Blue.copy(0.7f)
            ),
        ) {
            Text(text = title, textAlign = TextAlign.Start, modifier = modifier)
        }
    }
}

