package com.example.t_bank.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        is LoginState.Error -> {
                            showToast(state.message)
                        }
                    }
                }
            }
        }

        binding.apply {
            loginBtn.setOnClickListener {
                loginViewModel.login(login = loginEt.text.toString(), password = passwordEt.text.toString())
            }

            registerLink.setOnClickListener{
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

        }

    }

    private fun showToast(text: String){
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }
}