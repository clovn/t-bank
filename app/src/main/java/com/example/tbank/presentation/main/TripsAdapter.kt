package com.example.tbank.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.tbank.databinding.ItemMainBinding
import com.example.tbank.presentation.model.TripInfoView

class TripsAdapter(
    private var items: List<TripInfoView>,
    private val onTripClick: (Long, String, Int) -> Unit,
    private val onAddExpenses: (Long) -> Unit
): Adapter<TripViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TripViewHolder(
        binding = ItemMainBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onTripClick = onTripClick,
        onAddExpenses = onAddExpenses
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateList(newList: List<TripInfoView>){
        items = newList
    }
}