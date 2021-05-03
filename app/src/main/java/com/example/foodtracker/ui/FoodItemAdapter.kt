package com.example.foodtracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracker.R
import com.example.foodtracker.database.FoodItem
import com.example.foodtracker.databinding.FoodListItemBinding
import com.example.foodtracker.ui.FoodItemAdapter.ViewHolder
import java.text.SimpleDateFormat
import java.util.*

class FoodItemAdapter : ListAdapter<FoodItem, ViewHolder>(FoodItemDiffCallback()) {

    /**
     * Get the from the viewholder class
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    /**
     * This function is called to bind a view  to the view holder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the item in the list from the adapter
        var item = getItem(position)

        // Pass it to the bind method in the ViewHolder
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: FoodListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds a view to the ViewHolder. This this logic is handled here instead of the adapter
         */
        fun bind(item: FoodItem) {
            // Set the FoodItem in the binding object to the FoodItem from the list
            binding.foodItem = item

            // Optimization to update bindings right away. Its a good idea to always do this when
            // using binging adapters
            binding.executePendingBindings()
        }

        // Companion object will let us call functions without creating an instance of the class
        companion object {
            // Returns a ViewHolder for the RecyclerViewAdapter
            fun from(parent: ViewGroup): ViewHolder {
                // Get the LayoutInflater from the context of the parent ViewGroup
                val layoutInflater = LayoutInflater.from(parent.context)

                // Get the binding object by inflating from the binding class
                val binding = FoodListItemBinding.inflate(layoutInflater, parent, false)

                // Return the binding object wrapped in a ViewHolder
                return ViewHolder(binding)
            }
        }

    }

    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    class FoodItemDiffCallback : DiffUtil.ItemCallback<FoodItem>() {
        override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            return oldItem.foodId == newItem.foodId
        }

        override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            return oldItem == newItem
        }
    }

}