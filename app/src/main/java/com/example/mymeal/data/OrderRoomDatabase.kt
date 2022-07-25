package com.example.mymeal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OrderDetails::class], version = 1, exportSchema = false)
abstract class OrderRoomDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    companion object {

        @Volatile
        private var INSTANCE: OrderRoomDatabase? = null

        fun getDatabase(context: Context): OrderRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderRoomDatabase::class.java,
                    "order_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance

                return instance
            }
        }
    }
}