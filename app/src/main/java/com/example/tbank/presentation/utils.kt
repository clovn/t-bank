package com.example.tbank.presentation

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
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

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}

fun formatPhoneNumber(input: String): String {
    if (input.isEmpty() || !input.matches(Regex("""^\+?\d*$"""))) {
        return ""
    }

    var digits = ""

    digits = if(input.startsWith("+7")) input.substring(2)
    else if(input.startsWith("8") or input.startsWith("7")) input.substring(1)
    else input


    val fullNumber = if (digits.length < 11) {
        "7" + digits.padEnd(10, '_')
    } else {
        "7" + digits.takeLast(10)
    }

    val part1 = fullNumber.substring(0, 1)
    val part2 = fullNumber.substring(1, 4)
    val part3 = fullNumber.substring(4, 7)
    val part4 = fullNumber.substring(7, 9)
    val part5 = fullNumber.substring(9, 11)

    return "+$part1 $part2 $part3 $part4 $part5".replace("_", "")
}

fun normalizePhoneNumber(number: String): String {
    return number.replace(Regex("\\D"), "")
}

fun Int.dp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
)

fun dateFormat(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM")

    return date.format(formatter)
}

fun formatMoney(amount: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    formatter.isGroupingUsed = true

    return formatter.format(amount)
}

fun Int.dp(context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )

fun Int.sp(context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        context.resources.displayMetrics
    )
