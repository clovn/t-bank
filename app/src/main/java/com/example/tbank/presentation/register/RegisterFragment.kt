package com.example.tbank.presentation.register

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.FragmentRegisterBinding
import com.example.tbank.presentation.showError
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding: FragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                registerViewModel.uiState.collect { state ->
                    when(state){
                        is RegisterState.Idle -> {
                            binding.registerBtn.isEnabled = true
                        }
                        is RegisterState.Loading -> {
                            binding.registerBtn.isEnabled = false
                        }
                        is RegisterState.Success -> {
                            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                        }
                    }
                }
            }

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                registerViewModel.errorFlow.collect {error ->
                    showError(error)
                }
            }
        }
    }

    private fun initViews(){
        binding.apply {
            registerLink.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            registerBtn.setOnClickListener {
                val login = loginEt.text.toString().trim()
                val firstName = firstNameEt.text.toString().trim()
                val lastName = lastNameEt.text.toString().trim()
                val password = passwordEt.text.toString().trim()
                val number = numberEt.text.toString().trim()
                val confirmPassword = confirmPasswordEt.text.toString().trim()

                loginError.visibility = if(isValidLogin(login)) View.GONE else View.VISIBLE
                firstNameError.visibility = if(isValidName(firstName)) View.GONE else View.VISIBLE
                lastNameError.visibility = if(isValidName(lastName)) View.GONE else View.VISIBLE
                passwordError.visibility = if(isValidPassword(password)) View.GONE else View.VISIBLE
                numberError.visibility = if(isValidNumber(number)) View.GONE else View.VISIBLE
                confirmPasswordError.visibility = if(password == confirmPassword) View.GONE else View.VISIBLE

                if(isValidLogin(login) && isValidName(firstName) && isValidName(lastName) && isValidPassword(password) && isValidNumber(number) && password == confirmPassword) registerViewModel.register(loginEt.toString(), firstNameEt.toString(), lastNameEt.toString(), numberEt.toString(), passwordEt.toString(), confirmPasswordEt.toString())
            }
        }
    }


    private fun isValidLogin(login: String): Boolean {
        val regex = "^[a-zA-Z0-9_-]{3,20}$".toRegex()
        return regex.matches(login)
    }

    private fun isValidName(name: String): Boolean {
        val regex = "^[а-яА-Я]{3,20}$".toRegex()
        return regex.matches(name)
    }

    private fun isValidPassword(password: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%&*()]{8,}$".toRegex()
        return regex.matches(password)
    }

    private fun isValidNumber(phoneNumber: String): Boolean {
        val regex = "^[78]\\d{10}$".toRegex()
        return regex.matches(phoneNumber)
    }
}
