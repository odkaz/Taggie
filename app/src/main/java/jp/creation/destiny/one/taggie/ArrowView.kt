package drawable

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.transition.ChangeTransform
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import jp.creation.destiny.one.taggie.MyDragShadowBuilder
import jp.creation.destiny.one.taggie.R


class ArrowView(var posX: Int, var posY: Int, var degree: Float, context: Context): ImageView(context), View.OnClickListener, View.OnLongClickListener {
    fun inflate(parent: ViewGroup) {
        val arrow = LayoutInflater.from(context).inflate(R.layout.arrow_view, null)
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.leftMargin = posX
        params.topMargin = posY
        arrow.tag = "Arrow_View_Tag"
        parent.addView(arrow, params)
        arrow.visibility = View.VISIBLE
        arrow.setOnClickListener(this)
        arrow.setOnLongClickListener(this)

    }

    override fun onClick(v: View?) {
        val changeTransform = ChangeTransform()
        changeTransform.duration = 100
        changeTransform.interpolator = AccelerateInterpolator()
        TransitionManager.beginDelayedTransition(v!!.parent as ViewGroup, changeTransform)
        degree += 90f
        v.rotation = degree

    }

    override fun onLongClick(v: View?): Boolean {
        val item = ClipData.Item(v!!.tag as CharSequence)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(v.tag.toString(), mimeTypes, item)

        val myShadow = MyDragShadowBuilder(v)
        v.startDrag(
            data,
            myShadow,
            v,
            0

        )
        v.visibility = View.INVISIBLE

        return true
    }
}