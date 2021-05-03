package com.example.foodtracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


/**
 * Defines methods for using the FoodItem class with Room.
 */
@Dao
interface FoodDatabaseDao {
    /**
     * Insert a new item into the table
     */
    @Insert
    suspend fun insert(food: FoodItem)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     */
    @Update
    suspend fun update(food: FoodItem)

    /**
     * Selects and returns the row that matches the supplied primary key
     */
    @Query("SELECT * from food_item_table WHERE foodId = :key")
    fun get(key: Long): LiveData<FoodItem>



    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM food_item_table")
    suspend fun clear()


    /**
     * Selects and returns all rows in the table,
     *
     * sorted by date_in_milli in descending order.
     */
    @Query("SELECT * FROM food_item_table ORDER BY foodId DESC")
    fun getAllFoodItems(): LiveData<List<FoodItem>>
}