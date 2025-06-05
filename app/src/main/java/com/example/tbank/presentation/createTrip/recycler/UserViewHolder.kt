package com.example.tbank.presentation.createTrip.recycler

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tbank.databinding.ItemUserBinding
import com.example.tbank.domain.model.User
import com.example.tbank.presentation.formatPhoneNumber

class UserViewHolder(
    val binding: ItemUserBinding,
    val onClick: (User) -> Unit
) : ViewHolder(binding.root) {

    fun bind(user: User){
        binding.apply {
            icon.text = user.username.first().uppercaseChar().toString()
            name.text = user.username
            number.text = formatPhoneNumber(user.number)
        }
        binding.root.setOnClickListener {
            onClick(user)
        }
    }
}