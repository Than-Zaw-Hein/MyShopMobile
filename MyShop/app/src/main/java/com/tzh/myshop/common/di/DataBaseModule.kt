package com.tzh.myshop.common.di

import android.app.Application
import com.tzh.myshop.data.database.AppDatabase
import com.tzh.myshop.data.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(application: Application) = AppDatabase.getInstance(application)

    @Provides
    @Singleton
    fun productDao(appDatabase: AppDatabase): ProductDao = appDatabase.productDao()

    @Provides
    @Singleton
    fun stockInDao(appDatabase: AppDatabase): StockInDao = appDatabase.stockInDao()

    @Provides
    @Singleton
    fun stockOutDao(appDatabase: AppDatabase): StockOutDao = appDatabase.stockOutDao()

    @Provides
    @Singleton
    fun transactionHeaderDao(appDatabase: AppDatabase): TransactionHeaderDao = appDatabase.transactionHeaderDao()

    @Provides
    @Singleton
    fun transactionDao(appDatabase: AppDatabase): TransactionDao = appDatabase.transactionDao()

    @Provides
    @Singleton
    fun transactionDetailDao(appDatabase: AppDatabase): TransactionDetailDao = appDatabase.transactionDetailDao()
}