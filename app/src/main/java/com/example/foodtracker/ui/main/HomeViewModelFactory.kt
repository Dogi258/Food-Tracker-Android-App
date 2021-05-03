package com.example.foodtracker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodtracker.database.FoodDatabaseDao
import com.example.foodtracker.ui.newitem.NewFoodItemViewModel

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the FoodDatabaseDao to the ViewModel.
 */
class HomeViewModelFactory(
    private val dataSource: FoodDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}