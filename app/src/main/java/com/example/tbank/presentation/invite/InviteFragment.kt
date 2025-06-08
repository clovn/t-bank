package com.example.tbank.presentation.invite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.FragmentInvitationBinding
import com.example.tbank.presentation.observe
import com.example.tbank.presentation.showError
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class InviteFragment: Fragment(R.layout.fragment_invitation) {

    private val binding by viewBinding(FragmentInvitationBinding::bind)
    private val viewModel: InviteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun initViews(){
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }

            arguments?.let {bundle ->
                inviteText.text = bundle.getString("message")

                acceptInviteButton.setOnClickListener {
                    viewModel.invite(bundle.getInt("tripId"), true)
                }

                rejectInviteButton.setOnClickListener {
                    viewModel.invite(bundle.getInt("tripId"), false)
                }
            }
        }
    }

    private fun observe(){
        viewModel.state.observe(viewLifecycleOwner){state ->
            binding.apply {
                when(state){
                    is InviteState.Success -> {
                        findNavController().navigate(R.id.action_to_mainFragment)
                    }
                    is InviteState.Idle -> {
                        acceptInviteButton.isEnabled = true
                        rejectInviteButton.isEnabled = true
                    }
                    is InviteState.Loading -> {
                        acceptInviteButton.isEnabled = false
                        rejectInviteButton.isEnabled = false
                    }
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showError(it)
        }
    }
}
