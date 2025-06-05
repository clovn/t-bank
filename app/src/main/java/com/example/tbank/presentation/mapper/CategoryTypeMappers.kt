package com.example.tbank.presentation.mapper

import com.example.tbank.R
import com.example.tbank.domain.model.CategoryType

fun mapCategoryTypeDrawable(categoryType: CategoryType): Int {
    return when(categoryType){
        CategoryType.Tickets -> R.drawable.ic_ticket
        CategoryType.Hotel -> R.drawable.ic_hotel
        CategoryType.Food -> R.drawable.ic_food
        CategoryType.Entertainment -> R.drawable.ic_entertainment
        CategoryType.Insurance -> R.drawable.ic_insurance
        CategoryType.More -> R.drawable.ic_more
    }
}

fun mapCategoryTypeColor(categoryType: CategoryType): Int {
    return when(categoryType){
        CategoryType.Tickets -> R.color.ticket_color
        CategoryType.Hotel -> R.color.hotel_color
        CategoryType.Food -> R.color.food_color
        CategoryType.Entertainment -> R.color.entertainment_color
        CategoryType.Insurance -> R.color.insurance_color
        CategoryType.More -> R.color.other_color
    }
}

fun mapCategoryTypeText(categoryType: CategoryType): Int {
    return when(categoryType){
        CategoryType.Tickets -> R.string.tickets
        CategoryType.Hotel -> R.string.hotels
        CategoryType.Food -> R.string.food
        CategoryType.Entertainment -> R.string.entertainment
        CategoryType.Insurance -> R.string.insurance
        CategoryType.More -> R.string.more
    }
}