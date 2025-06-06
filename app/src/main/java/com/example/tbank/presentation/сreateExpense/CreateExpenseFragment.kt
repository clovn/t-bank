package com.example.tbank.presentation.сreateExpense

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tbank.R
import com.example.tbank.databinding.FragmentCreateExpenseBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding


@AndroidEntryPoint
class CreateExpenseFragment: Fragment(R.layout.fragment_create_expense) {

    private val binding by viewBinding(FragmentCreateExpenseBinding::bind)
    private val viewModel: CreateExpenseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews(){
        binding.apply {
            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.categories, R.layout.spinner_item
            )
            adapter.setDropDownViewResource(R.layout.spinner_item)
            spinnerCategory.adapter = adapter


        }
    }
}