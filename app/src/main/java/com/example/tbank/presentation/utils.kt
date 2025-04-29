package com.example.tbank.presentation

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showError(text: String){
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}