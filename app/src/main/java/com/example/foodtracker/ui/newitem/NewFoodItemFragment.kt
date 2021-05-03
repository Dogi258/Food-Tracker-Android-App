package com.example.foodtracker.ui.newitem

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodtracker.R
import com.example.foodtracker.database.FoodDatabase
import com.example.foodtracker.databinding.NewFoodItemFragmentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [NewFoodItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewFoodItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views
        var binding: NewFoodItemFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.new_food_item_fragment, container, false)

        // Get a reference to the application
        val application = requireNotNull(this.activity).application

        // Get an instance of the database Dao
        val dataSource = FoodDatabase.getInstance(application).foodDatabaseDao

        // Get an instance of the ViewModelFactory
        val viewModelFactory = NewFoodItemViewModelFactory(dataSource)

        // Get the ViewModel for this fragment using ViewModelProvider and passing in the viewModelFactory
        var viewModel =
            ViewModelProvider(this, viewModelFactory).get(NewFoodItemViewModel::class.java)

        // Set the binding viewModel to the fragment viewModel
        binding.viewModel = viewModel

        // Set the lifecycleOwner of the binding to this fragment
        binding.lifecycleOwner = this

        // Get a date picker instance with the default selection to today
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(viewModel.date.value)
                .setTitleText("Select date")
                .build()

        // Add click listener to the date field to show the date picker
        binding.dateField.editText?.setOnClickListener {
            datePicker.show(this.parentFragmentManager, "tag")
        }

        // Set the date in the viewModel when a date is selected
        datePicker.addOnPositiveButtonClickListener {
            viewModel.setDate(it)
        }

        // Observer event variable to navigate back to home when we submit data
        viewModel.navigateToHome.observe(
            this.viewLifecycleOwner,
            androidx.lifecycle.Observer { event ->
                // Only navigate if the event is true
                if (event == true) {
                    // Navigate
                    this.findNavController()
                        .navigate(NewFoodItemFragmentDirections.actionNewFoodItemFragmentToHomeFragment())

                    // Reset the event
                    viewModel.onNavigationToHomeComplete()
                }
            })

        // Return the root view of the binding
        return binding.root
    }
}