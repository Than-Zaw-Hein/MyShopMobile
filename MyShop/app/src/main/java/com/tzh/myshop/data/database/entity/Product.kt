package com.tzh.myshop.data.database.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tzh.myshop.data.database.TableNameConstant


@Entity(tableName = TableNameConstant.PRODUCT)
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "Product Name") var productName: String,
    @ColumnInfo(name = "Qty") var qty: Int,
    @ColumnInfo(name = "Current Qty") var currentQty: Int,
    @ColumnInfo(name = "Original Price") var originalPrice: Long,
    @ColumnInfo(name = "Selling Price") var sellingPrice: Long,
    @ColumnInfo(name = "profit") var profit: Long,
    @ColumnInfo(name = "createDate") var createDate: String?=null,
    @ColumnInfo(name = "updatedDate") var updatedDate: String? = null,
    @ColumnInfo(name = "ImageList") var imageList: List<String?> = listOf(null, null, null),
)
