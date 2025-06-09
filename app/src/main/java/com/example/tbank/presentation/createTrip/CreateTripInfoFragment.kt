package com.example.tbank.presentation.createTrip

import android.os.Bundle
import android.view.View
import androidx.core.util.Pair
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.FragmentCreateTripInfoBinding
import com.example.tbank.presentation.formatDate
import com.example.tbank.presentation.observe
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding
import java.time.ZoneId
import java.util.Date

@AndroidEntryPoint
class CreateTripInfoFragment: Fragment(R.layout.fragment_create_trip_info) {

    private val binding by viewBinding(FragmentCreateTripInfoBinding::bind)

    private val viewModel: CreateTripInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        binding.apply {
            nextBtn.setOnClickListener {
                viewModel.saveInfo()
                findNavController().navigate(R.id.action_to_createTripParticipants)
            }

            backBtn.setOnClickListener {
                findNavController().navigate(R.id.action_to_mainFragment)
            }

            dateIb.setOnClickListener {
                buildDatePicker { startDate, endDate ->
                    viewModel.setDates(
                        startDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(),
                        endDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    )

                    dateEt.setText(
                        getString(
                            R.string.date_format,
                            formatDate(startDate),
                            formatDate(endDate)
                        )
                    )
                }.show(parentFragmentManager, "DATE_RANGE_PICKER")
            }

            tripBudgetEt.addTextChangedListener {
                viewModel.onTripBudgetChanged(it.toString().trim())
            }

            tripNameEt.addTextChangedListener {
                viewModel.onTripNameChanged(it.toString().trim())
            }
        }
    }

    private fun observeData() {
        viewModel.formState.observe(viewLifecycleOwner) {
            binding.apply {
                tripNameError.visibility = if(it.tripNameState.isValid) View.GONE else View.VISIBLE
                tripBudgetError.visibility = if(it.tripBudgetState.isValid) View.GONE else View.VISIBLE
                nextBtn.isEnabled = it.isNextBtnActive
            }
        }
    }

    private fun buildDatePicker(onDatePicked: (startDate: Date, endDate: Date) -> Unit): MaterialDatePicker<Pair<Long, Long>> {
        val picker = MaterialDatePicker
            .Builder
            .dateRangePicker()
            .setTitleText("Выберите дату поездки")
            .build()

        picker.addOnPositiveButtonClickListener { dateRange ->
            val startDate = Date(dateRange.first)
            val endDate = Date(dateRange.second)
            onDatePicked(startDate, endDate)
        }

        return picker
    }
}