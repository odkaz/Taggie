package jp.creation.destiny.one.taggie

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout

class GiraffeOneView(var posX: Int, var posY: Int, var size: Int, val createFlag: Boolean, context: Context): ImageView(context), View.OnClickListener, View.OnLongClickListener {

    fun inflate(parent: ViewGroup) {
        val giraffeView = LayoutInflater.from(context).inflate(R.layout.giraffe_one_view, null) as ImageView
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.leftMargin = posX
        params.topMargin = posY
        params.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), resources.displayMetrics).toInt()
        params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), resources.displayMetrics).toInt()
        giraffeView.tag = "Giraffe_One_View_Tag"
        parent.addView(giraffeView, params)
        giraffeView.visibility = View.VISIBLE
        giraffeView.setOnClickListener(this)
        giraffeView.setOnLongClickListener(this)

    }

    override fun onClick(v: View?) {
        if (createFlag) {
            val params = v!!.layoutParams

            if (size == 200) {
                size = 100
                params.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), resources.displayMetrics).toInt()
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), resources.displayMetrics).toInt()

            } else {
                size = 200
                params.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), resources.displayMetrics).toInt()
                params.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  size.toFloat(), resources.displayMetrics).toInt()
            }
            v!!.layoutParams = params
        }

    }

    override fun onLongClick(v: View?): Boolean {
        if (createFlag) {
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
        }
        return true
    }
}