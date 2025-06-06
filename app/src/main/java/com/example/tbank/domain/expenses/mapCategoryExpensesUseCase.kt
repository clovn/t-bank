package com.example.tbank.domain.expenses

import com.example.tbank.domain.model.Category
import com.example.tbank.domain.model.Expense
import javax.inject.Inject

class mapCategoryExpensesUseCase @Inject constructor() {
    fun invoke(expenses: List<Expense>) = expenses.groupBy { it.type }.map{ (type, expenses) ->
        Category(type = type, amount = expenses.sumOf { it.amount })
    }
}