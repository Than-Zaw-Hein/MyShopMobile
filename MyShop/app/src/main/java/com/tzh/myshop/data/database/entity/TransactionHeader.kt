package com.tzh.myshop.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tzh.myshop.data.database.TableNameConstant

@Entity(tableName = TableNameConstant.TRANSACTIONHEADER)
data class TransactionHeader(

    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "TransactionType") val typeName: String,
    @ColumnInfo(name = "CreateDate") val createDate: String,
    @ColumnInfo(name = "Total Profit") val totalProfit: Long,
    @ColumnInfo(name = "Discount") var discount: Long = 0,
)
