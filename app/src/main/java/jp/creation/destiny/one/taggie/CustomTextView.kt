package jp.creation.destiny.one.taggie

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.widget.TextView

class CustomTextView(context: Context): TextView(context) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = createRedPaint()
        canvas!!.drawPoint(20f, 20f, paint)
    }

    private fun createRedPaint(): Paint {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint.strokeWidth = 10f
        return paint
    }


    fun drawPoint(point: Point) {
        val point = this.createRedPaint()
        //canvas.drawPoint(point.x, point.y, paint)
        invalidate()
    }
}