package jp.creation.destiny.one.taggie


import android.os.Bundle

import android.support.v7.app.AppCompatActivity;

import android.widget.*

import kotlinx.android.synthetic.main.activity_answer_creater.*


class AnswerCreaterActivity : AppCompatActivity(){
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var balloon: BalloonView

    /*
    companion object {
        val IMAGE_VIEW_TAG = "LAUNCHER LOGO"
        val TEXT_VIEW_TAG = "DRAG TEXT"
        val BUTTON_VIEW_TAG = "DRAG BUTTON"

    }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_creater)
        setSupportActionBar(toolbar)

        val relativeLayout = findViewById<RelativeLayout>(R.id.relative_layout)
        relativeLayout.setOnDragListener(MyOnDragListener(this))
        val itemLayout = findViewById<RelativeLayout>(R.id.item_layout)
        itemLayout.setOnDragListener(MyOnDragListener(this))


        balloon = BalloonView("title", "content", 0, 100, this)
        balloon.inflate(itemLayout)

    }
}