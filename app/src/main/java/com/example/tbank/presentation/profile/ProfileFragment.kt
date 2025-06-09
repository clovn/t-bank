package com.example.tbank.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.FragmentProfileBinding
import com.example.tbank.presentation.formatPhoneNumber
import com.example.tbank.presentation.observe
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
        viewModel.fetchUser()
    }

    private fun initViews(){
        binding.apply {
            logout.setOnClickListener {
                viewModel.logout()
            }
        }
    }

    private fun observe(){
        viewModel.state.observe(viewLifecycleOwner) {state ->
            binding.apply {
                when(state){
                    is ProfileState.Success -> {
                        val user = state.user
                        nameTv.text =
                            getString(R.string.nameTv_format, user.firstName, user.lastName)
                        numberTv.text = formatPhoneNumber(user.number)
                        icon.text = getString(
                            R.string.iconTv_format,
                            user.firstName.first(),
                            user.lastName.first()
                        )
                    }
                    is ProfileState.Loading -> {
                        nameTv.text = ""
                        numberTv.text = ""
                        icon.text = ""
                    }
                    is ProfileState.Error -> {

                    }
                    is ProfileState.Logout -> {
                        findNavController().navigate(R.id.action_to_loginFragment)
                    }
                }
            }
        }
    }
}