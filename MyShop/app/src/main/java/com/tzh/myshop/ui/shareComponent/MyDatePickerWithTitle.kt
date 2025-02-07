package com.tzh.myshop.ui.shareComponent

import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.tzh.myshop.R
import com.tzh.myshop.common.ulti.Constant
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyDatePickerWithTitle(
    title: String,
    defaultValue: String = SimpleDateFormat(
        "yyyy/MM/dd", Locale.getDefault()
    ).format(Calendar.getInstance().time),
    selectValue: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start, modifier = modifier.fillMaxWidth()
    ) {
        Text(buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold, fontSize = 18.sp, letterSpacing = 0.15.sp
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
                    text = defaultValue,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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
            MyDatePicker(onDateSelected = onDateSelected, onDismissRequest = {
                expanded.value = false
            })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(onDateSelected: (LocalDate) -> Unit, onDismissRequest: () -> Unit) {
    val selDate = remember { mutableStateOf(LocalDate.now()) }
    val dateState = rememberDatePickerState(
        selectableDates =
    )
    Dialog(
        onDismissRequest = onDismissRequest, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            DatePicker(
                state = dateState, colors = DatePickerDefaults.colors().copy(
                    todayDateBorderColor = Color.Red,
                    dateTextFieldColors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    dividerColor = MaterialTheme.colorScheme.primary,
                    yearContentColor = MaterialTheme.colorScheme.surface,
                )
            )

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                MyShopButton.TextButton(
                    onClick = onDismissRequest, title = "Cancel"
                )
                MyShopButton.TextButton(title = "OK") {
                    onDateSelected(milliToLocalDate(dateState.selectedDateMillis!!))
                    onDismissRequest()
                }
            }
        }


//        Column(
//            modifier = Modifier
//                .wrapContentSize()
//                .background(
//                    color = MaterialTheme.colors.surface, shape = RoundedCornerShape(size = 16.dp)
//                )
//        ) {
//            Column(
//                Modifier
//                    .defaultMinSize(minHeight = 72.dp)
//                    .fillMaxWidth()
//                    .background(
//                        color = MaterialTheme.colors.primary,
//                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
//                    )
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = "Select date".uppercase(),
//                    style = MaterialTheme.typography.caption,
//                )
//
//                Spacer(modifier = Modifier.size(24.dp))
//
//                Text(
//                    text = selDate.value.format(DateTimeFormatter.ofPattern("MMM d, YYYY")),
//                    style = MaterialTheme.typography.h4,
//                )
//
//                Spacer(modifier = Modifier.size(16.dp))
//            }
//
//
//            CustomCalendarView(onDateSelected = {
//                selDate.value = it
//            })
//
//            Spacer(modifier = Modifier.size(8.dp))
//

//        }
    }
}

fun milliToLocalDate(millis: Long): LocalDate {
    // Step 1: Convert milliseconds to Instant
    val instant = Instant.ofEpochMilli(millis)

    // Step 2: Convert Instant to LocalDateTime using the system default time zone
    val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()

    // Step 3: Extract the LocalDate from LocalDateTime
    return localDateTime.toLocalDate()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendarView(onDateSelected: (LocalDate) -> Unit) {
    // Adds view to Compose
    val context = LocalContext.current
    AndroidView(modifier = Modifier.wrapContentSize(), factory = { context ->
        CalendarView(context).apply {
            dateTextAppearance = R.style.CalenderViewDateCustomText
            weekDayTextAppearance = R.style.CalenderViewWeekCustomText
        }
    }, update = { view ->
        view.setOnDateChangeListener { _, year, month, dayOfMonth ->
            onDateSelected(
                LocalDate.now().withMonth(month + 1).withYear(year).withDayOfMonth(dayOfMonth)
            )
        }
    })
}