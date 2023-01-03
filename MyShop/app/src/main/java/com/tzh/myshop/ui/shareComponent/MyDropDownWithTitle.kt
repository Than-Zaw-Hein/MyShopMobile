package com.tzh.myshop.ui.shareComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault
import com.tzh.myshop.ui.shareComponent.Dimen.paddingSmall

@Composable
fun MyDropDownWithTitle(
    title: String,
    dropDownList: List<String>,
    isNecessary: Boolean = false,
    defaultValue: String = "",
    selectValue: (String) -> Unit,
    hasTitle: Boolean = true
) {
    Column(
        horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
    ) {
        if (hasTitle) {
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold, fontSize = 18.sp, letterSpacing = 0.15.sp
                    )
                ) {
                    append(title)
                }
                if (isNecessary) {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 18.sp, color = Color.Red,
                        )
                    ) {
                        append("*")
                    }
                }

            })
            Spacer(modifier = Modifier.height(paddingSmall))
        }
        // state of the menu
        val expanded = remember {
            mutableStateOf(false)
        }
        Box(modifier = Modifier
            .clickable {
                expanded.value = !expanded.value
            }
            .border(border = BorderStroke(1.dp, Color.Black))
            .padding(paddingDefault)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = defaultValue, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Dimen.DefaultMarginWidth()
                MyDropDown(expanded = expanded, list = dropDownList) {
                    selectValue(it)
                }
            }
        }
    }
}

@Composable
fun MyDropDown(expanded: MutableState<Boolean>, list: List<String>, onItemClick: (item: String) -> Unit) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.height(20.dp)
    ) {
        // options button
        IconButton(onClick = {
            expanded.value = true
        }) {
            Icon(
                imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                contentDescription = "Open Options"
            )
        }
        // drop down menu
        DropdownMenu(expanded = expanded.value, onDismissRequest = {
            expanded.value = false
        }) {
            list.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {
                        onItemClick.invoke(itemValue)
                        expanded.value = false
                    },
                ) {
                    Text(text = itemValue)
                }
            }
        }
    }
}