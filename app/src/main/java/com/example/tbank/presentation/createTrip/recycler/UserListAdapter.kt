package com.example.tbank.presentation.createTrip.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.tbank.databinding.ItemUserBinding
import com.example.tbank.domain.model.User

class UserListAdapter(
    val list: List<User>
): Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            binding = ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }
}