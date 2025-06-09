package com.example.tbank.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbank.R
import com.example.tbank.databinding.FragmentMainBinding
import com.example.tbank.domain.model.User
import com.example.tbank.presentation.dateFormat
import com.example.tbank.presentation.expenses.TRIP_BUDGET
import com.example.tbank.presentation.expenses.TRIP_ID
import com.example.tbank.presentation.expenses.TRIP_NAME
import com.example.tbank.presentation.formatMoney
import com.example.tbank.presentation.formatPhoneNumber
import com.example.tbank.presentation.model.TripInfo
import com.example.tbank.presentation.model.TripInfoView
import com.example.tbank.presentation.observe
import com.example.tbank.presentation.сreateExpense.TRIP_ID_KEY
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
        checkNotificationPermission()
        mainViewModel.fetchData()
    }

    private fun checkNotificationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
    }

    private fun initViews() {
        binding.apply {
            notificationBtn.setOnClickListener {
                findNavController().navigate(R.id.action_to_notifications_fragment)
            }

            tripsRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            tripsRv.adapter = TripsAdapter(
                emptyList(),
                { id, name, budget ->
                    findNavController().navigate(R.id.action_to_expenses_fragment, Bundle().apply {
                        putString(TRIP_NAME, name)
                        putLong(TRIP_ID, id)
                        putInt(TRIP_BUDGET, budget)
                    })
                },
                {id ->
                    findNavController().navigate(R.id.action_to_fragment_create_expense, Bundle().apply {
                        putInt(TRIP_ID_KEY, id.toInt())
                    })
                }
            )


            createTripBtn.setOnClickListener {
                findNavController().navigate(R.id.action_to_createTripInfoFragment)
            }

            nameTv.setOnClickListener {
                findNavController().navigate(R.id.action_to_fragment_profile)
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
                    showContent(state.trips, state.user)
                }
                is UiState.Error -> {
                    hideShimmer()
                    showError(message = state.message, mainViewModel::fetchData)
                }
            }
        }
    }

    private fun showContent(trips: List<TripInfo>, user: User?) {
        binding.apply {
            content.visibility = View.VISIBLE

            user?.let {
                nameTv.text = getString(R.string.name_format, user.firstName, user.lastName)
                numberTv.text = formatPhoneNumber(user.number)
            } ?: return

            if(trips.isNotEmpty()){
                noTrips.visibility = View.GONE
                tripsRv.visibility = View.VISIBLE
                (tripsRv.adapter as TripsAdapter).updateList(trips.map {
                    TripInfoView(
                        it.trip.id,
                        it.trip.name,
                        getString(R.string.date_format, dateFormat(it.trip.startDate), dateFormat(it.trip.endDate)),
                        it.trip.participantsCount,
                        it.trip.budget,
                        getString(R.string.format_money, formatMoney(it.trip.budget)),
                        getString(R.string.format_money, formatMoney(it.expensesSum)),
                        it.expensesSum*100 / it.trip.budget
                    )
                })
            } else {
                noTrips.visibility = View.VISIBLE
                tripsRv.visibility = View.GONE
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
