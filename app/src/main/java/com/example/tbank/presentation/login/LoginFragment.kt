package com.example.tbank.presentation.login

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.FragmentLoginBinding
import com.example.tbank.presentation.showError
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.uiState.collect { state ->
                    when(state){
                        is LoginState.Empty -> {
                            binding.loginBtn.isEnabled = true
                        }
                        is LoginState.Loading -> {
                            binding.loginBtn.isEnabled = false
                        }
                        is LoginState.Success -> {
                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                        }
                    }
                }
            }

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.errorFlow.collect { error ->
                    showError(error)
                }
            }
        }
    }

    private fun initViews(){
        binding.apply {
            loginBtn.setOnClickListener {
                loginViewModel.login(login = loginEt.text.toString(), password = passwordEt.text.toString())
            }

            registerLink.setOnClickListener{
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}
