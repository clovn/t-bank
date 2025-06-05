package com.example.tbank.presentation.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class IndentDecorator(
    private val marginHorizontal : Int,
    private val marginVertical: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            left = marginHorizontal
            right = marginHorizontal
            top = marginVertical
            bottom = marginVertical
        }
    }
}