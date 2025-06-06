package com.example.tbank.presentation.createTrip

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.BottomSheetBinding
import com.example.tbank.databinding.FragmentCreateTripExpensesBinding
import com.example.tbank.domain.model.Category
import com.example.tbank.domain.model.CategoryType
import com.example.tbank.presentation.customView.CategoryBadge
import com.example.tbank.presentation.formatMoney
import com.example.tbank.presentation.mapper.mapCategoryTypeColor
import com.example.tbank.presentation.mapper.mapCategoryTypeDrawable
import com.example.tbank.presentation.mapper.mapCategoryTypeText
import com.example.tbank.presentation.model.Segment
import com.example.tbank.presentation.observe
import com.example.tbank.presentation.showError
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class CreateTripExpensesFragment: Fragment(R.layout.fragment_create_trip_expenses) {

    private val binding by viewBinding(FragmentCreateTripExpensesBinding::bind)

    private val viewModel: CreateTripExpensesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun initViews(){
        binding.apply {
            CategoryType.entries.forEach { type ->
                categoriesContainer.addView(
                    CategoryBadge(requireContext()).apply {
                        setCategoryText(getString(mapCategoryTypeText(type)))
                        setLeftIcon(mapCategoryTypeDrawable(type))
                        setTint(R.color.light_gray)
                        setRightIcon(R.drawable.ic_add_category)
                        showPercent(true)
                        showRightButton(true)
                        setOnRightButtonClickListener {
                            toPickedCategory(this, type)
                        }
                    }
                )
            }

            createTripBtn.setOnClickListener {
                viewModel.save()
            }

            chartTv.text = formatMoney(viewModel.getBudget())

            chart.isFillGray(false)


            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun toPickedCategory(view: CategoryBadge, type: CategoryType){
        binding.apply {
            if(viewModel.getRemainingPercentage() == 0){
                Toast.makeText(requireContext(), "Уже использован весь бюджет", Toast.LENGTH_LONG).show()
                return
            }

            showBottomSheetDialog(viewModel.getRemainingPercentage(), viewModel.getBudget()) {
                categoriesContainer.removeView(view)

                val segment = Segment(
                    it.toFloat(),
                    ContextCompat.getColor(
                        requireContext(),
                        mapCategoryTypeColor(type)
                    )
                )
                val category = Category(type = type, amount = (viewModel.getBudget()*it) / 100, percent = it)

                view.setTint(mapCategoryTypeColor(type))
                view.setPercentText("$it%")
                view.setRightIcon(R.drawable.ic_delete_category)
                view.setOnRightButtonClickListener {
                    toUnpickedCategory(view, type, segment)
                }
                view.setCategory(category)

                pickedCategoriesContainer.addView(view)

                chart.addSegment(segment)

                viewModel.addCategory(category)
            }
        }
    }

    private fun toUnpickedCategory(view: CategoryBadge, type: CategoryType, segment: Segment){
        binding.apply {
            pickedCategoriesContainer.removeView(view)

            view.setTint(R.color.light_gray)
            view.setPercentText("")
            view.setRightIcon(R.drawable.ic_add_category)
            view.setOnRightButtonClickListener {
                toPickedCategory(view, type)
            }

            categoriesContainer.addView(view)
            chart.removeSegment(segment, )

            view.getCategory()?.let {
                viewModel.removeCategory(it)
            }
        }
    }

    private fun showBottomSheetDialog(remainingPercent: Int, maxAmount: Int, onDone: (Int) -> Unit) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogBinding = BottomSheetBinding.inflate(layoutInflater)
        var p = remainingPercent
        bottomSheetDialog.setContentView(dialogBinding.root)

        dialogBinding.apply {
            seekBar.progress = remainingPercent
            tvPercent.text = "$remainingPercent%"
            tvAmount.text = formatMoney(maxAmount*remainingPercent / 100)

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(progress > remainingPercent){
                        p = remainingPercent
                    } else {
                        p = progress
                    }

                    tvPercent.text = "$p%"
                    tvAmount.text = formatMoney(maxAmount*progress / 100)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            btnDone.setOnClickListener {
                onDone(p)
                bottomSheetDialog.dismiss()
            }
        }

        bottomSheetDialog.show()
    }

    private fun observe(){
        viewModel.state.observe(viewLifecycleOwner) {state ->
            binding.apply {
                when(state){
                    is State.Idle -> {
                        createTripBtn.isEnabled = true
                        createTripBtn.text = "Создать"
                    }
                    is State.Loading -> {
                        createTripBtn.isEnabled = false
                        createTripBtn.text = "Загрузка"
                    }
                    is State.Loaded -> {
                        findNavController().navigate(R.id.action_to_mainFragment)
                    }
                }
            }
        }

        viewModel.errorFlow.observe(viewLifecycleOwner) {
            showError(it)
        }
    }
}