package com.example.tbank.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.tbank.databinding.NameBadgeBinding

class Badge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private val binding: NameBadgeBinding =
        NameBadgeBinding.inflate(LayoutInflater.from(context), this, true)


    fun setName(name: String) {
        binding.nameTv.text = name
    }

    fun setOnRemoveClickListener(listener: OnClickListener?) {
        binding.deleteBtn.setOnClickListener(listener)
    }
}