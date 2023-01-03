package com.tzh.myshop.ui.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzh.myshop.common.ulti.Constant
import com.tzh.myshop.data.database.entity.TransactionDetail
import com.tzh.myshop.data.database.entity.TransactionHeader
import com.tzh.myshop.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TransactionViewModel @Inject constructor(
    val repository: MainRepository
) : ViewModel() {

    var transactionHeaderList = mutableStateListOf<TransactionHeader>()

    var typeList = mutableListOf<Constant.TransactionType>()
    var selectType = mutableStateOf("")
    val setSelectTypeList: (String) -> Unit = {
        selectType.value = it
    }

    var selectFromDate = mutableStateOf(Constant.getDateTime(LocalDate.now().withDayOfMonth(1)))
    val setSelectFromDate: (String) -> Unit = {
        selectFromDate.value = it
    }
    var selectToDate = mutableStateOf(Constant.getDateTime(LocalDate.now()))
    val setSelectToDate: (String) -> Unit = {
        selectToDate.value = it
    }

    init {
        getTypeList()
    }

    fun getTypeList() {
        typeList.clear()
        viewModelScope.launch {
            try {
                val list = repository.getTypeList()
                selectType.value = list[0].getTypeName()
                typeList.addAll(list)
                getTransactionHeader()
            } catch (e: Exception) {
            }
        }
    }

    fun getTransactionHeader() {
        transactionHeaderList.clear()
        viewModelScope.launch {
            try {
                var mList = if (selectType.value == Constant.TransactionType.ALL.getTypeName()) {
                    repository.getTransactionHeaderList(null, selectFromDate.value, selectToDate.value)
                } else {
                    repository.getTransactionHeaderList("${selectType.value}%", selectFromDate.value, selectToDate.value)
                }
                transactionHeaderList.addAll(mList)
            } catch (e: Exception) {
                Log.e("Get Transaction Error", e.toString())
            }
        }
    }


}