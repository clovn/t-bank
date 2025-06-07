package com.example.tbank.presentation.recyclerUserList

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
            icon.text = user.firstName.first().uppercaseChar().toString()
            name.text = user.firstName
            number.text = formatPhoneNumber(user.number)
        }
        binding.root.setOnClickListener {
            onClick(user)
        }
    }
}