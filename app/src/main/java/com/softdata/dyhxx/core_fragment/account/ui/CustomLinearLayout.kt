package com.softdata.dyhxx.core_fragment.account.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout

class CustomLinearLayout(context: Context) : LinearLayout(context) {

    private lateinit var mPaint:Paint
    private var mClickedChild = -1


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        initializeChildrenClickEvent()
    }

    init {
        createPaint()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mClickedChild != -1) {
            val child: View = getChildAt(mClickedChild)
            canvas!!.drawRect(
                child.left.toFloat(),
                child.top.toFloat(),
                child.right.toFloat(),
                child.bottom.toFloat(),
                mPaint
            )
        }
    }
    private fun initializeChildrenClickEvent() {
        val childCount = childCount
        val clickListener =
            OnClickListener { view ->
                for (i in 0 until childCount) {
                    if (getChildAt(i) == view) {
                        mClickedChild = i
                        break
                    }
                }
                invalidate()
            }
        for (i in 0 until childCount) {
            getChildAt(i).setOnClickListener(clickListener)
        }
    }

    private fun createPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.RED
        mPaint.strokeWidth = 5f
    }
}