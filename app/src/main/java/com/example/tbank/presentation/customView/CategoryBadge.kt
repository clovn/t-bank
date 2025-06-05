package com.example.tbank.presentation.customView

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.tbank.R
import com.example.tbank.databinding.ItemCategoryBinding

class CategoryBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ItemCategoryBinding = ItemCategoryBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private var showPercent = false
    private var rightButtonVisible = false

    init {
        orientation = HORIZONTAL
        gravity = android.view.Gravity.CENTER

        parseAttributes(attrs)
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        attrs?.let {attributes ->
            val a = context.obtainStyledAttributes(attributes, R.styleable.CategoryBadge)

            val leftIcon = a.getDrawable(R.styleable.CategoryBadge_leftIcon)
            val rightIcon = a.getDrawable(R.styleable.CategoryBadge_rightIcon)
            val categoryText = a.getString(R.styleable.CategoryBadge_categoryText)
            val percentText = a.getString(R.styleable.CategoryBadge_percentText)
            showPercent = a.getBoolean(R.styleable.CategoryBadge_percentTextVisible, false)
            val background = a.getColor(R.styleable.CategoryBadge_categoryBackground, context.getColor(R.color.category_default))
            rightButtonVisible = a.getBoolean(R.styleable.CategoryBadge_rightButtonVisible, false)

            binding.root.backgroundTintList = ColorStateList.valueOf(background)

            leftIcon?.let { binding.iconIv.setImageDrawable(it) }
            rightIcon?.let { binding.button.setImageDrawable(it) }

            binding.nameTv.text = categoryText ?: ""
            binding.percentTv.text = percentText ?: ""

            binding.percentTv.visibility = if (showPercent) VISIBLE else GONE
            binding.button.visibility = if (rightButtonVisible) VISIBLE else GONE

            a.recycle()
        }
    }


    fun setLeftIcon(drawableResId: Int) {
        binding.iconIv.setImageResource(drawableResId)
    }

    fun setRightIcon(drawableResId: Int) {
        binding.button.setImageResource(drawableResId)
    }

    fun setCategoryText(text: String) {
        binding.nameTv.text = text
    }

    fun setTint(color: Int) {
        binding.root.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, color))
    }

    fun setPercentText(text: String) {
        binding.percentTv.text = text
    }

    fun showPercent(show: Boolean) {
        showPercent = show
        binding.percentTv.visibility = if (show) VISIBLE else GONE
    }

    fun showRightButton(show: Boolean) {
        rightButtonVisible = show
        binding.button.visibility = if (show) VISIBLE else GONE
    }

    fun setOnRightButtonClickListener(listener: OnClickListener) {
        binding.button.setOnClickListener(listener)
    }
}