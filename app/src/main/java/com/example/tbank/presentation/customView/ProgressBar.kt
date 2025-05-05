package com.example.tbank.presentation.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.tbank.R

class ProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Int = 0
    private var text: String = ""

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val rect = RectF()

    init {
        progressPaint.color = context.getColor(R.color.yellow)
        backgroundPaint.color = context.getColor(R.color.chart_background)
        textPaint.color = context.getColor(R.color.hint_color)
        textPaint.textSize = 48f

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProgressBar)
            progress = typedArray.getInt(R.styleable.ProgressBar_progress, 0)
            text = typedArray.getString(R.styleable.ProgressBar_text) ?: ""
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        rect.set(0f, 0f, width, height)
        canvas.drawRoundRect(rect, height / 2, height / 2, backgroundPaint)

        val filledWidth = width * progress / 100f
        rect.set(0f, 0f, filledWidth, height)
        canvas.drawRoundRect(rect, height / 2, height / 2, progressPaint)

        if (text.isNotEmpty()) {
            val textWidth = textPaint.measureText(text)
            val x = filledWidth + 10f
            val y = height / 2 - (textPaint.descent() + textPaint.ascent()) / 2
            canvas.drawText(text, x, y, textPaint)
        }
    }

    fun setProgress(progress: Int) {
        this.progress = progress.coerceIn(0..100)
        invalidate()
    }

    fun setText(text: String) {
        this.text = text
        invalidate()
    }
}