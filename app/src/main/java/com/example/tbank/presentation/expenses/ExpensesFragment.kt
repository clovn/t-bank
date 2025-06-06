package com.example.tbank.presentation.expenses

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbank.R
import com.example.tbank.databinding.FragmentExpensesBinding
import com.example.tbank.domain.model.Category
import com.example.tbank.domain.model.Expense
import com.example.tbank.presentation.customView.CategoryBadge
import com.example.tbank.presentation.decorator.IndentDecorator
import com.example.tbank.presentation.formatMoney
import com.example.tbank.presentation.mapper.mapCategoryTypeColor
import com.example.tbank.presentation.mapper.mapCategoryTypeDrawable
import com.example.tbank.presentation.mapper.mapCategoryTypeText
import com.example.tbank.presentation.model.ExpenseView
import com.example.tbank.presentation.model.Segment
import com.example.tbank.presentation.observe
import com.example.tbank.presentation.showError
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

const val TRIP_NAME = "trip_name"
const val TRIP_BUDGET = "trip_budget"
const val TRIP_ID = "trip_id"

@AndroidEntryPoint
class ExpensesFragment: Fragment(R.layout.fragment_expenses) {

    companion object {
        const val ITEM_MARGIN_HORIZONTAL = 0
        const val ITEM_MARGIN_VERTICAL = 16
    }

    private val binding by viewBinding(FragmentExpensesBinding::bind)
    private val viewModel: ExpensesViewModel by viewModels()
    private var tripBudget = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
        arguments?.let {
            viewModel.fetchData(it.getLong(TRIP_ID))
        }
    }

    private fun observeData(){
        viewModel.expensesState.observe(viewLifecycleOwner) {state ->
            when(state){
                is ExpensesState.Loading -> {

                }
                is ExpensesState.Loaded -> {
                    setExpensesList(state.expensesList)
                    setCategories(state.categoriesList)
                }
                is ExpensesState.Error -> {
                    showError(state.message)
                }
            }
        }
    }

    private fun initViews(){
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }

            arguments?.let {
                tripBudget = it.getInt(TRIP_BUDGET)

                title.text = it.getString(TRIP_NAME)
                chartSubTitle.text = getString(R.string.chart_subtitle_format, formatMoney(tripBudget))
            }

            expensesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            expensesRv.addItemDecoration(IndentDecorator(ITEM_MARGIN_HORIZONTAL, ITEM_MARGIN_VERTICAL))
        }
    }

    private fun setExpensesList(expensesList: List<Expense>) {
        binding.apply {
            expensesRv.adapter = ExpenseAdapter(expensesList.map { expense ->
                ExpenseView(
                    name = expense.name,
                    typeDrawable = mapCategoryTypeDrawable(expense.type),
                    typeColorRes = ContextCompat.getColor(requireContext(), mapCategoryTypeColor(expense.type)),
                    authorName = expense.authorName,
                    amount = getString(R.string.format_expense, formatMoney(expense.amount))
                )
            })
        }
    }

    private fun setCategories(categoriesList: List<Category>) {
        binding.apply {
            categoriesList.forEach {category ->
                chartTitle.text = formatMoney(categoriesList.sumOf { it.amount })

                chart.addSegment(
                    Segment(
                        percentage = (category.amount * 100 / tripBudget).toFloat(),
                        color = ContextCompat.getColor(
                            requireContext(),
                            mapCategoryTypeColor(category.type)
                        )
                    )
                )

                categoriesContainer.addView(
                    CategoryBadge(requireContext()).apply {
                        setCategoryText(getString(mapCategoryTypeText(category.type)))
                        setLeftIcon(mapCategoryTypeDrawable(category.type))
                        setTint(mapCategoryTypeColor(category.type))
                    }
                )
            }
        }
    }
}