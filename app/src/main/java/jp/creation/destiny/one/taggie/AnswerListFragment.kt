package jp.creation.destiny.one.taggie

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import drawable.ArrowView

class AnswerListFragment(): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_answer, container, false) as ViewGroup
        val mDatabaseReference = FirebaseDatabase.getInstance().reference
        val qUid = arguments!!.getString("qUid", "")
        val answerRef = mDatabaseReference.child(ContentsPATH).child(qUid).child("answers").child("view")

        answerRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                //val data = HashMap<String, String>()
                children.forEach {
                    val data = it.value as HashMap<String, String>
                    Log.d("kotlintest", "data[type].toString is" + data["type"].toString())
                    when (data["type"]) {
                        "balloon" -> {
                            val balloonView = BalloonView(data["title"]!!, data["content"]!!, data["posX"]!!.toInt(), data["posY"]!!.toInt(), view.context)
                            balloonView.inflate(view)
                        }
                        "arrow" -> {
                            val arrowView = ArrowView(data["posX"]!!.toInt(), data["posY"]!!.toInt(), data["degree"]!!.toFloat(), view.context)
                            arrowView.inflate(view)
                        }

                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        return view
    }






}