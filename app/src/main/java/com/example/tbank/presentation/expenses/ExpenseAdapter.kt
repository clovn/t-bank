package com.example.tbank.presentation.expenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.tbank.databinding.ItemExpenseBinding
import com.example.tbank.presentation.model.ExpenseView

class ExpenseAdapter(
    private val list: List<ExpenseView>
): Adapter<ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpenseViewHolder(
        binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(list[position])
    }
}