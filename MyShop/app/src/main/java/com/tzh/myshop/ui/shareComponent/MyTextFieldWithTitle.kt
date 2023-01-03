package com.tzh.myshop.ui.shareComponent

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault
import com.tzh.myshop.ui.theme.primaryCharcoal

@Composable
fun MyTextFieldWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    isTypeNumber: Boolean = false,
    hasSuffix: Boolean = false
) {

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold, fontSize = 18.sp, letterSpacing = 0.15.sp
                    )
                ) {
                    append(title)
                }
            },
        )
        Box(
            modifier = Modifier
                .padding(top = paddingDefault)
                .fillMaxWidth()
                .height(35.dp)
                .border(color = primaryCharcoal, width = 1.dp)
        ) {
            BasicTextField(
                singleLine = true,
                keyboardOptions = if (isTypeNumber) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,
                modifier = Modifier
                    .matchParentSize()
                    .padding(paddingDefault)
                    .align(Alignment.CenterStart),
                value = value,
                onValueChange = onValueChange,
                visualTransformation = {
                    if (hasSuffix) {
                        TransformedText(
                            AnnotatedString(it.text + " MMK"), OffsetMapping.Identity
                        )
                    } else {
                        TransformedText(it, OffsetMapping.Identity)
                    }
                },
            )
        }
    }

}