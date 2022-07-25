package com.example.mymeal.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orderDetails")
data class OrderDetails(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "mainMeal") val mainMeal: String,
    @ColumnInfo(name = "salad") val salad: String,
    @ColumnInfo(name = "drink") val drink: String,
    @ColumnInfo(name = "dessert") val dessert: String,
    @ColumnInfo(name = "bookingDate") val bookingDate: String,
    @ColumnInfo(name = "total") val total: String
)