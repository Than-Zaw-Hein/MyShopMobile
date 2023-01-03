package com.tzh.myshop.ui.viewModel

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzh.myshop.R
import com.tzh.myshop.common.navigation.*
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.domain.repository.MainRepository
import com.tzh.myshop.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getData()
    }

    val setProductNameTextChange: (String) -> Unit = { text ->
        _uiState.update {
            it.copy(productName = text)
        }
    }

    fun getData(productName: String = "") {
        Log.e("GETTING DATA", productName)
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    productList = emptyList()
                )
            }
            val list = productRepository.getProduct(productName)
            Log.e("ASd", list.toString())
            _uiState.update {
                it.copy(
                    productList = list
                )
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                productRepository.deleteProduct(product)
                _uiState.update {
                    it.copy(isLoading = false, isSaveSuccess = true, error = null, isDelete = true)
                }

            } catch (e: Exception) {
                Log.e("ADASd", e.toString())
                _uiState.update {
                    it.copy(isLoading = false, error = e.message, isSaveSuccess = false, isDelete = true)
                }
            }
        }
    }

    fun doneAction() {
        _uiState.update { HomeUIState() }
        getData()
    }
}

data class HomeUIState(
    var productName: String = "",
    var productList: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isSaveSuccess: Boolean? = null,
    val error: String? = null,
    val isDelete: Boolean = false
)
