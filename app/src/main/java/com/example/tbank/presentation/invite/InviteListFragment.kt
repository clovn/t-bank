package com.example.tbank.presentation.invite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.FragmentInviteListBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class InviteListFragment: Fragment(R.layout.fragment_invite_list) {

    private val binding by viewBinding(FragmentInviteListBinding::bind)

//    private val viewModel: InviteListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initViews() {
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun observe() {

    }
}