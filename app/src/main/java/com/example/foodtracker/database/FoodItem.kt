package com.example.foodtracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Dataclass that represents our table in the database
 */
@Entity(tableName = "food_item_table")
data class FoodItem(

    @PrimaryKey(autoGenerate = true)
    var foodId: Long = 0L,

    @ColumnInfo(name = "date_in_milli")
    var dateInMilli: Long = -1L,

    @ColumnInfo(name = "food_text")
    var foodText: String = "",

    @ColumnInfo(name = "food_rating")
    var foodRating: Int = -1
    )