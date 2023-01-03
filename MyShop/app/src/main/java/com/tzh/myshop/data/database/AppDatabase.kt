package com.tzh.myshop.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tzh.myshop.data.database.Converter.Converters
import com.tzh.myshop.data.database.dao.*
import com.tzh.myshop.data.database.entity.Product
import com.tzh.myshop.data.database.entity.TransactionDetail
import com.tzh.myshop.data.database.entity.TransactionHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Database(
    entities = [
        Product::class,
        TransactionHeader::class,
        TransactionDetail::class,
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun stockOutDao(): StockOutDao
    abstract fun stockInDao(): StockInDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transactionHeaderDao(): TransactionHeaderDao
    abstract fun transactionDetailDao(): TransactionDetailDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(applicationContext: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    applicationContext, AppDatabase::class.java, "MyShopDataBase"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                // return instance
                instance
            }
        }


        private class TransactionTypeItemCallback(val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }

    }


}