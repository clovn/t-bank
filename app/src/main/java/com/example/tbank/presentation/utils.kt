package com.example.tbank.presentation

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Fragment.showError(text: String){
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}

inline fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: (T) -> Unit
): Job {
    return lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
            collect {
                block.invoke(it)
            }
        }
    }
}

fun dateFormat(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM")

    return date.format(formatter)
}

fun formatMoney(amount: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    formatter.isGroupingUsed = true

    return "${formatter.format(amount)} ₽"
}
