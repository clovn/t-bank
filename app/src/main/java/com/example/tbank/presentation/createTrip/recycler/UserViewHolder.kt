package com.example.tbank.presentation.createTrip.recycler

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tbank.databinding.ItemUserBinding
import com.example.tbank.domain.model.User

class UserViewHolder(
    val binding: ItemUserBinding
) : ViewHolder(binding.root) {

    fun bind(user: User){
        binding.apply {
            icon.text = user.username.first().uppercaseChar().toString()
            name.text = user.username
            number.text = user.number
        }
    }
}