package com.example.mymeal

import android.app.Application
import com.example.mymeal.data.OrderRoomDatabase

class OrderApplication : Application() {
    val database : OrderRoomDatabase by lazy { OrderRoomDatabase.getDatabase(this) }
}