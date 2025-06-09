package com.example.tbank.presentation.model

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

data class ExpenseView(
    val name: String,
    @DrawableRes val typeDrawable: Int,
    @ColorInt val typeColorRes: Int,
    val names: String,
    val amount: String
)
