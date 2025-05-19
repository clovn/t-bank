package com.example.tbank.presentation.register

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.FragmentRegisterBinding
import com.example.tbank.presentation.observe
import com.example.tbank.presentation.showError
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding


@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding: FragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
    }

    private fun observeData(){
        registerViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when(state){
                is RegisterState.Idle -> {
                    binding.registerBtn.isEnabled = true
                }
                is RegisterState.Loading -> {
                    binding.registerBtn.isEnabled = false
                }
                is RegisterState.Success -> {
                    findNavController().navigate(R.id.action_to_mainFragment)
                }
            }
        }

        registerViewModel.errorFlow.observe(viewLifecycleOwner) { error ->
            showError(error)
        }

        registerViewModel.formState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                loginError.visibility = if (state.loginState.isValid) View.GONE else View.VISIBLE
                firstNameError.visibility = if (state.firstNameState.isValid) View.GONE else View.VISIBLE
                lastNameError.visibility = if (state.lastNameState.isValid) View.GONE else View.VISIBLE
                passwordError.visibility = if (state.passwordState.isValid) View.GONE else View.VISIBLE
                numberError.visibility = if (state.numberState.isValid) View.GONE else View.VISIBLE
                confirmPasswordError.visibility = if (state.confirmPasswordState.isValid) View.GONE else View.VISIBLE

                registerBtn.isEnabled = state.isRegisterBtnActive
            }
        }
    }

    private fun initViews(){
        binding.apply {
            registerLink.setOnClickListener {
                findNavController().navigate(R.id.action_to_loginFragment)
            }

            registerBtn.setOnClickListener {
                registerViewModel.register()
            }

            val currentState = registerViewModel.formState.value

            loginEt.apply {
                setText(currentState.loginState.value)
                addTextChangedListener { registerViewModel.onLoginChanged(it.toString().trim()) }
            }

            firstNameEt.apply {
                setText(currentState.firstNameState.value)
                addTextChangedListener { registerViewModel.onFirstNameChanged(it.toString().trim()) }
            }

            lastNameEt.apply {
                setText(currentState.lastNameState.value)
                addTextChangedListener { registerViewModel.onLastNameChanged(it.toString().trim()) }
            }

            numberEt.apply {
                setText(currentState.numberState.value)
                addTextChangedListener { registerViewModel.onNumberChanged(it.toString().trim()) }
            }

            passwordEt.apply {
                setText(currentState.passwordState.value)
                addTextChangedListener { registerViewModel.onPasswordChanged(it.toString().trim()) }
            }

            confirmPasswordEt.apply {
                setText(currentState.confirmPasswordState.value)
                addTextChangedListener { registerViewModel.onConfirmPasswordChanged(it.toString().trim()) }
            }
        }
    }

}
