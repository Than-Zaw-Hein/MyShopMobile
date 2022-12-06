package com.tzh.myshop.ui.viewModel

import android.app.Application
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzh.myshop.common.ulti.Constant
import com.tzh.myshop.common.ulti.Extension.toPrice
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StockInViewModel @Inject constructor(
    private val productRepository: ProductRepository, private val application: Application
) : ViewModel() {
    private val _uiState = MutableStateFlow(StockInUiState())
    val uiState = _uiState.asStateFlow()

    var imageList = mutableStateListOf<String?>(null, null, null)

    var productName by mutableStateOf("")
    var qty by mutableStateOf("0")
    var qtyTextChange: (String) -> Unit = {
        qty = it.toPrice()
    }
    var originalPrice by mutableStateOf("0")
    var orgPriceTextChange: (String) -> Unit = {
        originalPrice = it.toPrice()
        calculateProfit()
    }

    var sellingPrice by mutableStateOf("0")
    var sellingPriceTextChange: (String) -> Unit = {
        sellingPrice = it.toPrice()
        calculateProfit()
    }
    var profit by mutableStateOf("0")

    private fun calculateProfit() {
        val sellPrice = sellingPrice.toLong()
        val orgPrice = originalPrice.toLong()
        profit = (sellPrice - orgPrice).toString()
    }

    fun createProduct() {
        if (productName.isEmpty()) {
            _uiState.update {
                it.copy(isLoading = false, error = "Please enter product name")
            }
            return
        }

        val product = Product(
            productName = productName,
            qty = qty.toInt(),
            currentQty = qty.toInt(),
            originalPrice = originalPrice.toLong(),
            sellingPrice = sellingPrice.toLong(),
            createDate = Constant.getCurrentTime(),
            profit = profit.toLong(),
            imageList = imageList
        )

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                productRepository.createProduct(product)
                _uiState.update {
                    it.copy(isLoading = false, isSaveSuccess = true, error = null)
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message, isSaveSuccess = false)
                }
            }
        }
    }

    fun addImage(uri: Uri, index: Int) {
        try {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(
                    application.contentResolver, uri
                )
            } else {
                val source = ImageDecoder.createSource(application.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
            imageList[index] = uri.toString()
        } catch (e: Exception) {
            Log.e("ASDASd", e.message.toString())
        }
    }

    fun doneShowErrorMessage() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    fun doneAction() {
        _uiState.update { StockInUiState() }
    }
}


data class StockInUiState(
    val isLoading: Boolean = false,
    val isSaveSuccess: Boolean? = null,
    val error: String? = null,
)