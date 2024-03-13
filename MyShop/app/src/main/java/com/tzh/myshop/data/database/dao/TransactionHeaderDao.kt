package com.tzh.myshop.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tzh.myshop.data.database.entity.TransactionHeader
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionHeaderDao {
    @Query("SELECT * FROM TransactionHeader WHERE  CreateDate BETWEEN :startDate AND :endDate  ORDER BY Id DESC")
    suspend fun getAllTransaction(startDate: String, endDate: String): List<TransactionHeader>

    @Query(
        """SELECT * FROM TransactionHeader 
            WHERE  TransactionType LIKE :typeName 
            and  CreateDate  BETWEEN  :startDate 
            AND :endDate ORDER BY Id DESC"""
    )
    suspend fun getTransactionByTransactionType(
        typeName: String? = null, startDate: String, endDate: String
    ): List<TransactionHeader>

    @Insert()
    suspend fun insert(transactionHeader: TransactionHeader): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg transactionHeader: TransactionHeader): List<Long>

}