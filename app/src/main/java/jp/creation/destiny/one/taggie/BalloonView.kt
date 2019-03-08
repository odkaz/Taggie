package jp.creation.destiny.one.taggie

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView


class BalloonView(private var title: String, private var content: String, private var posX: Int, private var posY: Int, context: Context, private var parent: ViewGroup): TextView(context), View.OnLongClickListener {
    init {


    }

    private fun action() {
        val balloon = LayoutInflater.from(context).inflate(R.layout.balloon_view, null)
        Log.d("kotlintest", "init baloonView")
        balloon.tag = "Balloon_View_Tag"
        parent.addView(balloon)
        balloon.visibility = View.VISIBLE
        balloon.setOnLongClickListener(this)



    }

    override fun onLongClick(v: View?): Boolean {
        Log.d("kotlintest", "balloon long click")

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
        val dragListener = MyOnDragListener(context)

        v.visibility = View.INVISIBLE



        return true
    }




}