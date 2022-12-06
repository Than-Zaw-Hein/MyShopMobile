package com.tzh.myshop.ui.shareComponent

import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tzh.myshop.common.ulti.Constant
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyDatePickerWithTitle(
    title: String,
    defaultValue: String = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Calendar.getInstance().time),
    selectValue: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start, modifier = modifier.fillMaxWidth()
    ) {
        Text(buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold, fontSize = 20.sp, letterSpacing = 0.15.sp
                )
            ) {
                append(title)
            }
        })
        Spacer(modifier = Modifier.height(Dimen.paddingSmall))

        val expanded = remember {
            mutableStateOf(false)
        }
        Box(modifier = Modifier
            .clickable {
                expanded.value = !expanded.value
            }
            .border(border = BorderStroke(1.dp, Color.Black))
            .padding(Dimen.paddingDefault)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = defaultValue, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Dimen.DefaultMarginWidth()
                MyDickPickerDropDown(expanded) {
                    selectValue.invoke(Constant.getDateTime(it))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyDickPickerDropDown(expanded: MutableState<Boolean>, onDateSelected: (LocalDate) -> Unit) {
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
        if (expanded.value) {
            DatePicker(onDateSelected = onDateSelected, onDismissRequest = {
                expanded.value = false
            })
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(onDateSelected: (LocalDate) -> Unit, onDismissRequest: () -> Unit) {
    val selDate = remember { mutableStateOf(LocalDate.now()) }

    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface, shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 72.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Select date".uppercase(),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(24.dp))

                Text(
                    text = selDate.value.format(DateTimeFormatter.ofPattern("MMM d, YYYY")),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(16.dp))
            }

            CustomCalendarView(onDateSelected = {
                selDate.value = it
            })

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "Cancel", style = MaterialTheme.typography.button, color = MaterialTheme.colors.onPrimary
                    )
                }
                MyShopButton.TextButton(
                    onClick = onDismissRequest, title = "Cancel"
                )
                MyShopButton.TextButton(title = "OK") {
                    onDateSelected(selDate.value)
                    onDismissRequest()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendarView(onDateSelected: (LocalDate) -> Unit) {
    // Adds view to Compose
    AndroidView(modifier = Modifier.wrapContentSize(), factory = { context ->
        CalendarView(context)
    }, update = { view ->

        view.setOnDateChangeListener { _, year, month, dayOfMonth ->
            onDateSelected(
                LocalDate.now().withMonth(month + 1).withYear(year).withDayOfMonth(dayOfMonth)
            )
        }
    })
}