package com.example.tbank.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.tbank.databinding.NameBadgeBinding
import com.example.tbank.domain.model.User

class Badge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val onRemoved: (User) -> Unit,
    val user: User
) : FrameLayout(context, attrs, defStyleAttr) {


    private val binding: NameBadgeBinding =
        NameBadgeBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.apply {
            deleteBtn.setOnClickListener {
                if(parent is ViewGroup){
                    (parent as ViewGroup).removeView(this@Badge)
                }

            }

            nameTv.text = user.username
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onRemoved(user)
    }
}