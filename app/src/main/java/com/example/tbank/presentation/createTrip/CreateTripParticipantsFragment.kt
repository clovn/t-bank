package com.example.tbank.presentation.createTrip

import androidx.fragment.app.Fragment
import com.example.tbank.R
import com.example.tbank.databinding.FragmentCreateTripParticipantsBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class CreateTripParticipantsFragment: Fragment(R.layout.fragment_create_trip_participants) {

    private val binding by viewBinding(FragmentCreateTripParticipantsBinding::bind)

    private fun initViews() {
        binding.apply {
            
        }
    }
}