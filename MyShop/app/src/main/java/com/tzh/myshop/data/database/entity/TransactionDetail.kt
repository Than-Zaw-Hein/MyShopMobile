package com.tzh.myshop.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tzh.myshop.data.database.TableNameConstant

@Entity(tableName = TableNameConstant.TRANSACTIONDEATIL)
data class TransactionDetail(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "TransactionHeaderId") val transactionHeaderId: Long,
    @ColumnInfo(name = "ProductID") var productId: Long,
    @ColumnInfo(name = "Qty") var qty: Int,
    @ColumnInfo(name = "Product Name") var productName: String,
    @ColumnInfo(name = "Original price") var originalPrice: Long,
    @ColumnInfo(name = "Selling price") var sellingPrice: Long,
    @ColumnInfo(name = "Profit") var profit: Long,
    @ColumnInfo(name = "CreateDate") var createDate: String,
)
