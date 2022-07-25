package com.example.mymeal.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(orderDetails: OrderDetails)

    @Delete
    suspend fun delete(orderDetails: OrderDetails)

    @Query("SELECT * FROM orderDetails ORDER BY id ASC")
    fun getAllOrders(): Flow<List<OrderDetails>>
}