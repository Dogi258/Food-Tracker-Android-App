package com.example.foodtracker.ui.main

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.foodtracker.R
import com.example.foodtracker.database.FoodDatabase
import com.example.foodtracker.databinding.HomeFragmentBinding
import com.example.foodtracker.ui.FoodItemAdapter
import com.example.foodtracker.ui.newitem.NewFoodItemViewModel
import com.example.foodtracker.ui.newitem.NewFoodItemViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views
        var binding: HomeFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        // Get a reference to the application
        val application = requireNotNull(this.activity).application

        // Get an instance of the database Dao and pass in the application
        val dataSource = FoodDatabase.getInstance(application).foodDatabaseDao

        // Get an instance of the ViewModelFactory and pass in the datasource
        val viewModelFactory = HomeViewModelFactory(dataSource)

        // Get the ViewModel for this fragment using ViewModelProvider and passing in the viewModelFactory
        var viewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        // Set the binding viewModel to the fragment viewModel
        binding.viewModel = viewModel

        // Set the lifecycleOwner of the binding to this fragment
        binding.lifecycleOwner = this

        binding.newFoodItemButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewFoodItemFragment())
        }

        // Create an instance of FoodItemAdapter then set it to the adapter in the binding object
        val adapter = FoodItemAdapter()
        binding.foodList.adapter = adapter

        // When the FoodList data changes, submit it the the recycler view adapter
        viewModel.foodItemList.observe(this.viewLifecycleOwner, Observer {
            // Observe only when the list is not null
            it?.let {
                adapter.submitList(it)
            }
        })

        // Return the root view of the data binding
        return binding.root
    }
}