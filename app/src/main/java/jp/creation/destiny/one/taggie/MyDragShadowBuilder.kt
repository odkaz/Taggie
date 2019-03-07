package jp.creation.destiny.one.taggie

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.View

class MyDragShadowBuilder(v: View): View.DragShadowBuilder(v) {
    private val shadow = ColorDrawable(Color.LTGRAY)

    override fun onProvideShadowMetrics(outShadowSize: Point, outShadowTouchPoint: Point) {
        val width = view.width / 2
        val height = view.height / 2

        shadow.setBounds(0, 0, width, height)
        outShadowSize.set(width, height)
        outShadowTouchPoint.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas?) {
        shadow.draw(canvas)
    }
}