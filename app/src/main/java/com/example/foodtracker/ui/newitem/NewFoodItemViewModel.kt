package com.example.foodtracker.ui.newitem

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.foodtracker.database.FoodDatabaseDao
import com.example.foodtracker.database.FoodItem
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

/**
 * ViewModel for NewFoodItemFragment.
 */
class NewFoodItemViewModel(private val database: FoodDatabaseDao) : ViewModel() {


    /**
     *  viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * The date the food item will be logged for in UtcMilliseconds
     */
    private val _date = MutableLiveData<Long>()
    val date: LiveData<Long>
        get() = _date

    /**
     * The formatted string of the date.
     * To be used for the date field in the fragment
     */


    var dateString = Transformations.map(_date) { date ->
        // Instantiate a SimpleDateFormat object to format the date
        var dateFormatter = SimpleDateFormat("MM/dd/yy")

        // Change the timezone to UTC because the date is in UTC milli
        dateFormatter.timeZone = TimeZone.getTimeZone("UTC")

        // Returns the formatted string of the calendar date
        dateFormatter.format(Date(date))
    }

    /**
     * LiveData for the text string for food in the fragment
     * This variable is used directly in the xml layout for two way data binding
     */
    val foodString = MutableLiveData<String>()

    /**
     * LiveData for the food quality
     */
    private var _foodQuality = MutableLiveData<Int>()
    val foodQuality: MutableLiveData<Int>
        get() = _foodQuality

    /**
     * To be called after inserting into the database to signal navigation
     */
    private var _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: MutableLiveData<Boolean>
        get() = _navigateToHome


    // Things that happen when this viewModel gets created
    init {
        // Set the date to the current date when the viewModel gets created
        setDate(MaterialDatePicker.todayInUtcMilliseconds())

        // Set the food quality to 1, which translates the the  radio button that will be check
        // when the fragment is created
        setFoodQuality(1)
    }

    /**
     * This public function sets the date of the viewModel
     */
    public fun setDate(date: Long) {
        _date.value = date
    }

    /**
     * This public function sets the rating from the radio buttions
     */
    public fun setFoodQuality(rating: Int) {
        _foodQuality.value = rating
    }


    /**
     * Resets the navigation event variable once navigation is done in the fragment
     */
    public fun onNavigationToHomeComplete() {
        _navigateToHome.value = false
    }

    /**
     * When the log food button is clicked, create an instance of FoodItem and insert it to
     * the database
     */
    public fun onLogFoodClicked() {
        // Start a coroutine in the UI scope
        uiScope.launch {

            // Make a new FoodItem and assign its properties
            val newFoodItem = FoodItem()
            newFoodItem.dateInMilli = _date.value ?: -1
            newFoodItem.foodText = foodString.value ?: ""
            newFoodItem.foodRating = _foodQuality.value ?: -1

            //Insert the foodItem into the database on a background thread
            insert(newFoodItem)

            // Tells the fragment that its ready to navigate
            _navigateToHome.value = true
        }
    }

    /**
     * Insert a foodItem into the database
     */
    private suspend fun insert(newFoodItem: FoodItem) {
        withContext(Dispatchers.IO) {
            database.insert(newFoodItem)
        }
    }

    /**
     * When the ViewModel is dismantled, cancel all coroutines; otherwise we end up with processes
     * that have nowhere to return to using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

