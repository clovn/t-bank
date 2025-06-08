package com.example.tbank.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.tbank.R
import com.example.tbank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val navController = findNavController(R.id.navHost)
        intent?.extras?.let {
            val type = it.getString("type")
            when(type){
                "INVITATION" -> {
                    navController.navigate(R.id.action_to_invite_fragment, Bundle().apply {
                        putInt("tripId", it.getInt("tripId"))
                        putString("message", it.getString("message"))
                    })
                }
                "EXPENSE_ADD" -> {
                    navController.navigate(R.id.action_to_expenses_fragment, Bundle().apply {
                        putInt("tripId", it.getInt("tripId"))
                    })
                }
            }
        }
    }
}
