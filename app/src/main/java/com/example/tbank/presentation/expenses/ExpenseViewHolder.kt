package com.example.tbank.presentation.expenses

import android.content.res.ColorStateList
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tbank.databinding.ItemExpenseBinding
import com.example.tbank.presentation.model.ExpenseView

class ExpenseViewHolder(
    private val binding: ItemExpenseBinding
): ViewHolder(binding.root) {

    fun bind(expense: ExpenseView){
        binding.apply {
            ImageViewCompat.setImageTintList(iconBackIv, ColorStateList.valueOf(expense.typeColorRes))
            iconIv.setImageResource(expense.typeDrawable)
            categoryTv.text = expense.name
            subtitleTv.text = expense.authorName
            priceTv.text = expense.amount
        }
    }
}