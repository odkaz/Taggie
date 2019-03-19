package jp.creation.destiny.one.taggie


import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import drawable.ArrowView

import kotlinx.android.synthetic.main.activity_answer_creater.*


class AnswerCreaterActivity : AppCompatActivity(){
    private val TAG = AnswerCreaterActivity::class.java.simpleName
    private lateinit var balloon: BalloonView
    private lateinit var arrow: ArrowView
    private lateinit var giraffeOne: GiraffeOneView
    private lateinit var mDatabaseReference: DatabaseReference
    private var itemLayoutFlag = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_creater)
        setSupportActionBar(toolbar)

        title = "Create your answer"

        val relativeLayout = findViewById<RelativeLayout>(R.id.relative_layout)
        relativeLayout.setOnDragListener(MyOnDragListener(this))
        val itemLayout = findViewById<RelativeLayout>(R.id.item_layout)
        itemLayout.setOnDragListener(MyOnDragListener(this))

        itemLayout.visibility = View.GONE

        fab.setOnClickListener { v ->
            if (itemLayoutFlag) {
                itemLayout.visibility = View.VISIBLE
                balloon = BalloonView("title", "content", 20, 50, true,  this)
                balloon.inflate(itemLayout)

                arrow = ArrowView(300, 50, 0f, true, this)
                arrow.inflate(itemLayout)

                giraffeOne = GiraffeOneView(450, 0, 200, true, this)
                giraffeOne.inflate(itemLayout)

                itemLayoutFlag = false

            } else {
                itemLayout.removeAllViews()
                itemLayout.visibility = View.GONE

                itemLayoutFlag = true

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_answer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.save -> {
                val relativeLayout = findViewById<RelativeLayout>(R.id.relative_layout)
                retrieveAllViews(relativeLayout)

            }
        }
        return true
    }

    private fun retrieveAllViews(v: ViewGroup) {
        val extras = intent.extras
        val qUid = extras.getString("qUid", "")

        mDatabaseReference = FirebaseDatabase.getInstance().reference
        val answerRef = mDatabaseReference.child(ContentsPATH).child(qUid).child("answers")
        val key = answerRef.push().key
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
            } else if (v.getChildAt(i).id == R.id.giraffe1View) {
                val mGiraffe = v.getChildAt(i) as ImageView
                data["type"] = "giraffe"
                data["size"] = (mGiraffe.layoutParams.width
                        / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f,
                        resources.displayMetrics).toInt() * 100).toString()
            }

            data["posX"] = childView.x.toInt().toString()
            data["posY"] = childView.y.toInt().toString()

            answerRef.child(key!!).push().setValue(data)
        }
        val currentUser = FirebaseAuth.getInstance().currentUser
        val answerHistoryRef = mDatabaseReference.child(UsersPATH).child(currentUser!!.uid).child("answer_history")

        answerHistoryRef.child(key!!).setValue(key)

        finish()
    }


}