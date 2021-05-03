package com.example.foodtracker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.foodtracker.database.FoodDatabaseDao
import com.example.foodtracker.database.FoodItem

class HomeViewModel(private val database: FoodDatabaseDao) : ViewModel() {

    /**
     * Observable List of fooditems LiveData
     */
    val foodItemList = database.getAllFoodItems()
}