package com.example.tbank.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.FragmentMainBinding
import com.example.tbank.domain.model.Trip
import com.example.tbank.domain.model.User
import com.example.tbank.presentation.dateFormat
import com.example.tbank.presentation.expenses.TRIP_BUDGET
import com.example.tbank.presentation.expenses.TRIP_ID
import com.example.tbank.presentation.expenses.TRIP_NAME
import com.example.tbank.presentation.formatMoney
import com.example.tbank.presentation.formatPhoneNumber
import com.example.tbank.presentation.observe
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val mainViewModel: MainViewModel by viewModels()
    private var trip: Trip? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()

        mainViewModel.fetchData()
    }

    private fun initViews() {
        binding.apply {
            notificationBtn.setOnClickListener {
                //TODO navigate to notifications screen
            }
            tripInfo.setOnClickListener {
                findNavController().navigate(R.id.action_to_expenses_fragment, Bundle().apply {
                    trip?.let {
                        putString(TRIP_NAME, it.name)
                        putLong(TRIP_ID, it.id)
                        putInt(TRIP_BUDGET, it.budget)
                    }
                })
            }
            addExpenses.setOnClickListener {
                //TODO navigate to add expenses
            }

            createTripBtn.setOnClickListener {
                findNavController().navigate(R.id.action_to_createTripInfoFragment)
            }
        }
    }

    private fun observeData() {
        mainViewModel.uiState.observe(viewLifecycleOwner) {state ->
            when(state){
                is UiState.Loading -> {
                    hideContent()
                    hideError()
                    showShimmer()
                }
                is UiState.Loaded -> {
                    hideShimmer()
                    trip = state.trip
                    showContent(state.trip, state.user, state.expensesSum)
                }
                is UiState.Error -> {
                    hideShimmer()
                    showError(message = state.message, mainViewModel::fetchData)
                }
            }
        }
    }

    private fun showContent(trip: Trip?, user: User?, expensesSum: Int?) {
        binding.apply {
            content.visibility = View.VISIBLE
            tripInfo.visibility = View.GONE
            tripExpenses.visibility = View.GONE
            addExpenses.visibility = View.GONE

            user?.let {
                nameTv.text = getString(R.string.name_format, user.firstName, user.lastName)
                numberTv.text = formatPhoneNumber(user.number)
            } ?: return

            if(trip != null && expensesSum != null){
                tripNameTv.text = trip.name
                tripDateTv.text = getString(
                    R.string.date_format,
                    dateFormat(trip.startDate),
                    dateFormat(trip.endDate)
                )
                tripPeopleCountTv.text = trip.participantsCount.toString()
                tripBudgetTv.text = getString(R.string.format_money, formatMoney(trip.budget))
                expenseTv.text = getString(R.string.format_money, formatMoney(expensesSum))
                tripExpensesPb.setProgress(expensesSum * 100 / trip.budget)

                tripInfo.visibility = View.VISIBLE
                tripExpenses.visibility = View.VISIBLE
                addExpenses.visibility = View.VISIBLE
            }
        }
    }

    private fun hideContent() {
        binding.content.visibility = View.GONE
    }

    private fun showError(message: String, onRetry: () -> Unit) {
        binding.apply {
            error.retryButton.setOnClickListener{ onRetry() }
            error.errorTitle.text = message
            errorContainer.visibility = View.VISIBLE
        }
    }

    private fun hideError() {
        binding.errorContainer.visibility = View.GONE
    }

    private fun showShimmer() {
        binding.shimmerLayout.visibility = View.VISIBLE
    }

    private fun hideShimmer() {
        binding.shimmerLayout.visibility = View.GONE
    }
}
