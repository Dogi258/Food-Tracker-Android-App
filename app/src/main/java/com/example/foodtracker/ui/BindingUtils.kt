package com.example.foodtracker.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodtracker.R
import com.example.foodtracker.database.FoodItem
import java.text.SimpleDateFormat
import java.util.*


/**
 * Convert the foodItem date to a formatted string and sets the text
 */
@BindingAdapter("date")
fun TextView.setDate(item: FoodItem?){
    item?.let {
        // Get a formatter with a pattern
        var dateFormatter = SimpleDateFormat("MM/dd/yy")

        // Change the timezone to UTC because the date is in UTC milli
        dateFormatter.timeZone = TimeZone.getTimeZone("UTC")

        // Set the text the formatted string of the date
        text = dateFormatter.format(Date(item.dateInMilli))
    }
}

/**
 * Gets the food string from the FoodItem and sets the text
 */
@BindingAdapter("foodString")
fun TextView.setFoodString(item: FoodItem?){
    item?.let {
        text = item.foodText
    }
}

/**
 * Sets the image of an image view depending of the food quality
 */
@BindingAdapter("qualityImage")
fun ImageView.setQualityImage(item: FoodItem?){
    item?.let {
        setImageResource(when (item.foodRating) {
            1 -> R.drawable.ic_happy
            2 -> R.drawable.ic_neutral
            else -> R.drawable.ic_sad
        })
    }
}