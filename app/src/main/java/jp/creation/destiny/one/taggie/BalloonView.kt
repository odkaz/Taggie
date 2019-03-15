package jp.creation.destiny.one.taggie

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.test_text_view.view.*


class BalloonView(var title: String, var content: String, var posX: Int, var posY: Int, context: Context): TextView(context), View.OnClickListener, View.OnLongClickListener{

    fun inflate(parent: ViewGroup) {
        val balloon = LayoutInflater.from(context).inflate(R.layout.balloon_view, null) as TextView
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.leftMargin = posX
        params.topMargin = posY
        Log.d("kotlintest", "init baloonView")
        balloon.text = title
        parent.addView(balloon, params)
        balloon.visibility = View.VISIBLE
        balloon.setOnClickListener(this)
        balloon.setOnLongClickListener(this)

    }

    override fun onClick(v: View?) {
        Log.d("kotlintest", "Balloon clicked")
        val alert = AlertDialog.Builder(context)
        val editTitle = EditText(context)
        val editContent = EditText(context)
        val linearLayout = LinearLayout(context)
        //putting edit title and content in the linearlayout, because alert dialog can take only one view
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(editTitle)
        linearLayout.addView(editContent)
        alert.setTitle("please enter content")
        alert.setView(linearLayout)
        alert.setPositiveButton("OK") {_, _ ->
            title = editTitle.text.toString()
            Log.d("kotlintest", title)
            val viewAsTextView = v as TextView
            viewAsTextView.text = title
            Log.d("kotlintest", "layout params is =" + viewAsTextView.layoutParams.toString())
            content = editContent.text.toString()
            viewAsTextView.tag = editContent.text.toString()
            Log.d("kotlintest", content)
        }
        alert.create()
        alert.show()
    }

    override fun onLongClick(v: View?): Boolean {
        Log.d("kotlintest", "balloon long click")
        Log.d("kotlintest", "v is = " + v.toString())

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