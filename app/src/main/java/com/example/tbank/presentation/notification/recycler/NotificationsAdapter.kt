package com.example.tbank.presentation.notification.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.tbank.databinding.ItemInviteBinding
import com.example.tbank.domain.model.NotificationModel

class NotificationsAdapter(
    var list: List<NotificationModel>,
    val onClick: (Int, Int, String) -> Unit
): Adapter<NotificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotificationViewHolder(
        binding = ItemInviteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onClick = onClick
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(newList: List<NotificationModel>){
        list = newList
        notifyDataSetChanged()
    }
}