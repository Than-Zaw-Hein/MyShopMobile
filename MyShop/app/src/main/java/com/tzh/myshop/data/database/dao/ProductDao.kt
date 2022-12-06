package com.tzh.myshop.data.database.dao

import androidx.room.*
import com.tzh.myshop.data.database.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product order by `Product Name`")
    suspend fun getAllProduct(): List<Product>

    @Query("SELECT * FROM Product WHERE id =:id ")
    suspend fun findProductById(id: Long): Product

    @Query("SELECT * FROM Product WHERE `Product Name` LIKE :productName order by `Product Name`")
    suspend fun findByProductName(productName: String): List<Product>

    @Update()
    suspend fun update(product: Product)

    @Insert()
    suspend fun insert(product: Product): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg product: Product): List<Long>

    @Delete
    suspend fun delete(product: Product)

}