package com.tzh.myshop.common.ulti

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object Constant {

    const val dateFormatPattern = "dd/MM/yyyy"

    enum class TransactionType ( id: Int, name: String) {
        ALL(0, "ALL"), UPDATEITEM(1, "Update Item"), STOCKIN(2, "Stock In"), SALE(3, "Sale"), DELETE(4, "Delete");

        private var _id = id;
        fun getId(): Long = _id.toLong()

        private var _name = name;
        fun getTypeName(): String = _name
    }

    fun getCurrentTime(): String {
        return SimpleDateFormat(dateFormatPattern, Locale.getDefault()).format(
            Calendar.getInstance().time
        )
    }

    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatPattern)

    fun getDateTime(localDate: LocalDate): String {
        return localDate.format(formatter)
    }


    @SuppressLint("UnrememberedGetBackStackEntry")
    @Composable
    fun NavBackStackEntry.rememberParentEntry(navController: NavController): NavBackStackEntry {
        // First, get the parent of the current destination
        // This always exists since every destination in your graph has a parent
        val parentId = this.destination.parent!!.id

        // Now get the NavBackStackEntry associated with the parent
        // making sure to remember it
        return remember {
            navController.getBackStackEntry(parentId)
        }
    }
}

public enum class PopUpState {
    OPEN, CLOSE
}
