package com.tzh.myshop.ui.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tzh.myshop.common.ulti.Constant
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.data.database.entity.TransactionDetail
import com.tzh.myshop.data.database.entity.TransactionHeader
import com.tzh.myshop.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    val repository: MainRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionDetailUiState())
    val uiState = _uiState.asStateFlow()


    init {
        val header = Gson().fromJson<TransactionHeader>(
            savedStateHandle.get<String>("header").toString(), object : TypeToken<TransactionHeader>() {}.type
        )
        val fromDate = savedStateHandle.get<String>("fromDate").toString()
        val toDate = savedStateHandle.get<String>("toDate").toString()
        getTransactionDetailList(header, fromDate, toDate)
    }


    private fun getTransactionDetailList(header: TransactionHeader, fromDate: String, toDate: String) {
        viewModelScope.launch {
            val mList = repository.getTransactionDetailList(header.id!!)

            _uiState.update {
                it.copy(
                    transactionDetail = mList,
                    transactionHeader = header,
                    isSale = header.typeName == Constant.TransactionType.SALE.getTypeName(),
                    selectedFromDate = fromDate,
                    selectedToDate = toDate
                )
            }

        }
    }
}

data class TransactionDetailUiState(
    val transactionDetail: List<TransactionDetail> = emptyList(),
    val transactionHeader: TransactionHeader? = null,
    val selectedFromDate: String = "",
    val selectedToDate: String = "",
    var isSale: Boolean = false
)