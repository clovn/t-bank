package com.example.tbank.presentation.notification.recycler

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tbank.databinding.ItemInviteBinding
import com.example.tbank.domain.model.NotificationModel
import com.example.tbank.domain.model.NotificationType

class NotificationViewHolder(
    val binding: ItemInviteBinding,
    val onClick: (Int, Int, String) -> Unit
): ViewHolder(binding.root) {

    fun bind(notification: NotificationModel){
        binding.categoryTv.text = notification.message
        if(notification.type == NotificationType.INVITATION){
            binding.root.setOnClickListener {
                onClick(notification.id, notification.tripId, notification.message)
            }
        }
    }
}