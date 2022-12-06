package com.tzh.myshop.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.data.database.entity.TransactionDetail
import com.tzh.myshop.data.database.entity.TransactionHeader
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDetailDao {

    @Query("SELECT * FROM TransactionDetail Where TransactionHeaderId = :transactionHeaderId")
    suspend fun getAllTransactionDetailByID(transactionHeaderId: Long): List<TransactionDetail>

    @Insert()
    suspend fun insert(transactionDetail: TransactionDetail): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg transactionDetail: TransactionDetail): List<Long>
}