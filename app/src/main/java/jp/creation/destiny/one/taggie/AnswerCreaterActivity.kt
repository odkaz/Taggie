package jp.creation.destiny.one.taggie


import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import drawable.ArrowView

import kotlinx.android.synthetic.main.activity_answer_creater.*


class AnswerCreaterActivity : AppCompatActivity(){
    private val TAG = AnswerCreaterActivity::class.java.simpleName
    private lateinit var balloon: BalloonView
    private lateinit var arrow: ArrowView
    private lateinit var mDatabaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_creater)
        setSupportActionBar(toolbar)

        val relativeLayout = findViewById<RelativeLayout>(R.id.relative_layout)
        relativeLayout.setOnDragListener(MyOnDragListener(this))
        val itemLayout = findViewById<RelativeLayout>(R.id.item_layout)
        itemLayout.setOnDragListener(MyOnDragListener(this))


        balloon = BalloonView("title", "content", 20, 50, this)
        balloon.inflate(itemLayout)

        arrow = ArrowView(300, 50, 0f, this)
        arrow.inflate(itemLayout)

        fab.setOnClickListener { v ->
            retrieveAllViews(relativeLayout)
        }
    }

    private fun retrieveAllViews(v: ViewGroup) {
        val extras = intent.extras
        val qUid = extras.getString("qUid", "")

        mDatabaseReference = FirebaseDatabase.getInstance().reference
        val answerRef = mDatabaseReference.child(ContentsPATH).child(qUid).child("answers")
        val answerViewRef = answerRef.child("view")

        for (i in 0 until v.childCount) {
            val data = HashMap<String, String>()
            val childView = v.getChildAt(i)

            if (v.getChildAt(i).id == R.id.balloonView) {
                val mBalloon = v.getChildAt(i) as TextView
                data["type"] = "balloon"
                data["title"] = mBalloon.text.toString()
                data["content"] = mBalloon.tag.toString()


            } else if (v.getChildAt(i).id == R.id.arrowView) {
                val mArrow = v.getChildAt(i) as ImageView
                data["type"] = "arrow"
                data["degree"] = mArrow.rotation.toString()

            }

            data["posX"] = childView.x.toInt().toString()
            data["posY"] = childView.y.toInt().toString()

            val key = answerRef.push().key
            answerViewRef.child(key.toString()).setValue(data)

        }
        finish()

    }
}