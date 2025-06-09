package com.example.tbank.presentation.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbank.R
import com.example.tbank.databinding.FragmentNotificationsBinding
import com.example.tbank.presentation.decorator.IndentDecorator
import com.example.tbank.presentation.notification.recycler.NotificationsAdapter
import com.example.tbank.presentation.observe
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class NotificationsFragment: Fragment(R.layout.fragment_notifications) {

    private val binding by viewBinding(FragmentNotificationsBinding::bind)

    private val viewModel: NotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchNotifications()
        initViews()
        observe()
    }

    private fun initViews() {
        binding.notificationsRv.adapter = NotificationsAdapter(emptyList()) { id, tripId, message ->
            findNavController().navigate(R.id.action_to_invite_fragment, Bundle().apply {
                putInt("tripId", tripId)
                putString("message", message)
                putInt("id", id)
            })
        }
        binding.notificationsRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.notificationsRv.addItemDecoration(IndentDecorator(0, 8))
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observe() {
        viewModel.state.observe(viewLifecycleOwner){ state->
            binding.apply {
                when(state){
                    is NotificationsState.Loaded -> {
                        (notificationsRv.adapter as NotificationsAdapter).updateList(state.list)
                    }
                    is NotificationsState.Loading -> {

                    }
                }
            }
        }
    }
}