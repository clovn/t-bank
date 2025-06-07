package com.example.tbank.presentation.recyclerUserList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.tbank.databinding.ItemUserBinding
import com.example.tbank.domain.model.User

class UserListAdapter(
    private var list: List<User>,
    private val onClick: (User) -> Unit
): Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            binding = ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setList(list: List<User>){
        this.list = list
        notifyDataSetChanged()
    }
}