package com.tzh.myshop.domain.repository

import com.tzh.myshop.common.ulti.Constant
import com.tzh.myshop.data.database.dao.TransactionDao
import com.tzh.myshop.data.database.dao.TransactionDetailDao
import com.tzh.myshop.data.database.dao.TransactionHeaderDao
import com.tzh.myshop.data.database.entity.TransactionDetail
import com.tzh.myshop.data.database.entity.TransactionHeader
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val transactionHeaderDao: TransactionHeaderDao,
    private val transactionDetailDao: TransactionDetailDao,
    private val transactionDao: TransactionDao
) {
    fun getTypeList() = listOf(
        Constant.TransactionType.ALL,
        Constant.TransactionType.STOCKIN,
        Constant.TransactionType.SALE,
        Constant.TransactionType.UPDATEITEM,
        Constant.TransactionType.DELETE
    )

    suspend fun getTransactionHeaderList(typeName: String? = null, startDate: String, endDate: String): List<TransactionHeader> {
        return if (typeName != null) {
            transactionHeaderDao.getTransactionByTransactionType(typeName, startDate, endDate)
        } else {
            transactionHeaderDao.getAllTransaction(startDate, endDate)
        }
    }

    suspend fun getTransactionDetailList(transactionHeaderId: Long): List<TransactionDetail> {
        return transactionDetailDao.getAllTransactionDetailByID(transactionHeaderId)
    }

}