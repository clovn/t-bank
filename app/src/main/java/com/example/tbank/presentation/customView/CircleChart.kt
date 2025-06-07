package com.example.tbank.presentation.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.tbank.R
import com.example.tbank.presentation.dp
import com.example.tbank.presentation.model.Segment

class CircleChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val segments = mutableListOf<Segment>()
    private var totalPercentage = 0f
    private var ringRadius = 0f
    private val ringThickness = 20.dp(context)
    private var fillGray = true

    private val segmentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = ringThickness
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ringRadius = (minOf(w, h) / 2 - ringThickness / 2)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var startAngle = -90f
        for (segment in segments) {
            val sweepAngle = segment.percentage * 3.6f

            segmentPaint.color = segment.color

            canvas.drawArc(
                width / 2f - ringRadius,
                height / 2f - ringRadius,
                width / 2f + ringRadius,
                height / 2f + ringRadius,
                startAngle,
                sweepAngle,
                false,
                segmentPaint
            )

            startAngle += sweepAngle
        }

        segmentPaint.color = context.getColor(R.color.chart_background)

        canvas.drawArc(
            width / 2f - ringRadius,
            height / 2f - ringRadius,
            width / 2f + ringRadius,
            height / 2f + ringRadius,
            startAngle,
            (100 - totalPercentage)*3.6f,
            false,
            segmentPaint
        )
    }

    fun addSegment(segment: Segment) {
        if (totalPercentage + segment.percentage > 100) {
            Log.d("ERROR", "Общий процент не может превышать 100")
            return
        }
        segments.add(segment)
        totalPercentage += segment.percentage
        Log.d("DEBUG", "${segment.percentage}")
        invalidate()
    }

    fun removeSegment(segment: Segment) {
        segments.remove(segment)
        totalPercentage -= segment.percentage
        invalidate()
    }

    fun setFillGray(fillGray: Boolean){
        this.fillGray = fillGray
        invalidate()
    }

    fun clearSegments() {
        segments.clear()
        totalPercentage = 0f
        invalidate()
    }
}