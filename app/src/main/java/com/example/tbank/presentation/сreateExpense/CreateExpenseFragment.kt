package com.example.tbank.presentation.сreateExpense

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbank.R
import com.example.tbank.databinding.FragmentCreateExpenseBinding
import com.example.tbank.presentation.customView.Badge
import com.example.tbank.presentation.decorator.IndentDecorator
import com.example.tbank.presentation.mapper.mapTextCategoryType
import com.example.tbank.presentation.observe
import com.example.tbank.presentation.recyclerUserList.UserListAdapter
import com.example.tbank.presentation.showError
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding


const val TRIP_ID_KEY = "trip_id"

@AndroidEntryPoint
class CreateExpenseFragment: Fragment(R.layout.fragment_create_expense) {

    private val binding by viewBinding(FragmentCreateExpenseBinding::bind)
    private val viewModel: CreateExpenseViewModel by viewModels()

    companion object {
        const val ITEM_MARGIN_HORIZONTAL = 0
        const val ITEM_MARGIN_VERTICAL = 8
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val tripId = it.getInt(TRIP_ID_KEY)
            viewModel.fetchParticipants(tripId)
            viewModel.fetchCategories(tripId)
        }

        initViews()
        observe()
    }

    private fun initViews(){
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                viewModel.categories.value.map { str -> getString(str) }
            )

            adapter.setDropDownViewResource(R.layout.spinner_item)
            spinnerCategory.adapter = adapter

            participantsRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            participantsRv.addItemDecoration(IndentDecorator(ITEM_MARGIN_HORIZONTAL, ITEM_MARGIN_VERTICAL))

            paidEt.addTextChangedListener {
                val list = viewModel.getMatchedParticipants(it.toString())
                rvCard.visibility = if(list.isNotEmpty()) View.VISIBLE else View.GONE
                participantsRv.adapter = UserListAdapter(
                    list = list,
                    onClick = {user ->
                        val badge = Badge(requireContext(), user = user, onRemoved = { u ->
                            viewModel.removeParticipant(u)
                        })

                        viewModel.addParticipant(user)
                        personContainer.addView(badge)

                        paidEt.setText("")
                    }
                )
            }

            addExpenseBtn.setOnClickListener {
                arguments?.let{
                    viewModel.addExpense(it.getInt(TRIP_ID_KEY), mapTextCategoryType(spinnerCategory.selectedItem.toString()), descriptionEt.text.toString(), amountEt.text.toString().toInt())
                }
            }
        }
    }

    private fun observe(){
        viewModel.uiState.observe(viewLifecycleOwner) {state ->
            binding.apply {
                when(state){
                    is AddExpenseState.Idle -> {
                        addExpenseBtn.isEnabled = true
                        addExpenseBtn.text = getString(R.string.add)
                    }
                    is AddExpenseState.Loading -> {
                        addExpenseBtn.isEnabled = false
                        addExpenseBtn.text = getString(R.string.loading)
                    }
                    is AddExpenseState.Success -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }

        viewModel.errorFlow.observe(viewLifecycleOwner){
            showError(it)
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            Log.d("DEBUG", it.map { str -> getString(str) }.toString())
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                it.map { str -> getString(str) }
            )

            adapter.setDropDownViewResource(R.layout.spinner_item)
            binding.spinnerCategory.adapter = adapter
        }
    }
}