package com.example.tbank.presentation.createTrip

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbank.R
import com.example.tbank.databinding.FragmentCreateTripParticipantsBinding
import com.example.tbank.presentation.createTrip.recycler.UserListAdapter
import com.example.tbank.presentation.customView.Badge
import com.example.tbank.presentation.observe
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class CreateTripParticipantsFragment: Fragment(R.layout.fragment_create_trip_participants) {

    private val binding by viewBinding(FragmentCreateTripParticipantsBinding::bind)
    private val viewModel: CreateTripParticipantsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observe()
    }

    private fun initViews() {
        binding.apply {
            usersRv.adapter = UserListAdapter(listOf()) { user ->

                val badge = Badge(requireContext(), user = user, onRemoved = { u ->
                    viewModel.removeParticipant(u)
                })

                viewModel.addParticipant(user)
                participantsBox.addView(badge)

                searchEt.setText("")
                searchEt.clearFocus()
            }
            usersRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            searchEt.setText("")

            searchEt.addTextChangedListener {
                 viewModel.renderQuery(it.toString())
            }

            nextBtn.setOnClickListener {
                viewModel.saveInfo()
                findNavController().navigate(R.id.action_to_createTripExpensesFragment)
            }

            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun observe() {
        viewModel.state.observe(viewLifecycleOwner) {
            binding.apply {
                (usersRv.adapter as UserListAdapter).setList(it.participants)
            }
        }
    }
}