package com.example.tbank.presentation.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tbank.databinding.ItemMainBinding
import com.example.tbank.presentation.model.TripInfoView

class TripViewHolder(
    private val binding: ItemMainBinding,
    val onTripClick: (Long, String, Int) -> Unit,
    val onAddExpenses: (Long) -> Unit
): ViewHolder(binding.root) {
    fun bind(tripInfo: TripInfoView){
        binding.apply {
            tripNameTv.text = tripInfo.name
            tripDateTv.text = tripInfo.date
            tripPeopleCountTv.text = tripInfo.participantsCount.toString()
            tripBudgetTv.text = tripInfo.budgetString
            expenseTv.text = tripInfo.expenses
            tripExpensesPb.setProgress(tripInfo.progress)

            this.tripInfo.visibility = View.VISIBLE
            tripExpenses.visibility = View.VISIBLE
            addExpenses.visibility = View.VISIBLE

            this.tripInfo.setOnClickListener {
                onTripClick(tripInfo.id, tripInfo.name, tripInfo.budget)
            }

            addExpensesBtn.setOnClickListener {
                onAddExpenses(tripInfo.id)
            }

            addExpenses.setOnClickListener {
                onAddExpenses(tripInfo.id)
            }
        }
    }
}